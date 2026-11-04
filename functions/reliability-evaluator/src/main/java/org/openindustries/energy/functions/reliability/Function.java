package org.openindustries.energy.functions.reliability;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares outage minutes and availability against asset targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String assetId, int measuredOutageMinutes, int targetOutageMinutes,
                      double measuredAvailabilityPct, double targetAvailabilityPct) {
    }

    public record Verdict(String assetId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict reliabilityEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredOutageMinutes() > kpi.targetOutageMinutes()) {
            findings.add("outageMinutes " + kpi.measuredOutageMinutes() + " exceeds target " + kpi.targetOutageMinutes());
        }
        if (kpi.measuredAvailabilityPct() < kpi.targetAvailabilityPct()) {
            findings.add("availabilityPct " + kpi.measuredAvailabilityPct() + " below target " + kpi.targetAvailabilityPct());
        }
        return new Verdict(kpi.assetId(), findings.isEmpty(), findings);
    }
}
