# Grid Rules Service

Bounded context: **Grid Rules** — Decision rules binding zone types to load and outage envelopes.

Alignment: Operating envelopes as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/grid-rules` |
| Owns data | GridRule rules |
| Publishes | `grid.activated` |

Run locally: `mvn -pl services/grid-rules-service quarkus:dev`.
