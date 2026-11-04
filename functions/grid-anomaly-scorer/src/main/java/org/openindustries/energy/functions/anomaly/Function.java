package org.openindustries.energy.functions.anomaly;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores grid deviation signals (voltage, frequency, losses) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String assetId, int voltageDeviationIndex, int frequencyDeviationIndex, int lossIndex) {
    }

    public record Score(String assetId, int score, String decision, String rationale) {
    }

    @Funq
    public Score gridAnomalyScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.voltageDeviationIndex() >= 70) {
            score += 40;
            why.append("voltageDeviationIndex >= 70; ");
        }
        if (event.frequencyDeviationIndex() >= 60) {
            score += 30;
            why.append("frequencyDeviationIndex >= 60; ");
        }
        if (event.lossIndex() >= 50) {
            score += 30;
            why.append("lossIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.assetId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
