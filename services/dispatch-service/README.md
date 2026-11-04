# Dispatch Service

Bounded context: **Dispatch** — Guarded dispatch capacity per asset and the dispatch orders against it.

Alignment: Operations capacity discipline. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/dispatch` |
| Owns data | DispatchCapacity accounts, DispatchOrder records |
| Publishes | `dispatch.consumed`, `dispatch.insufficient` |

Run locally: `mvn -pl services/dispatch-service quarkus:dev`.
