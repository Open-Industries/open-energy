# Reliability Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares outage minutes and availability against asset targets; one finding per breach.

Invoke: `POST /reliabilityEvaluator` with the KPI payload.
