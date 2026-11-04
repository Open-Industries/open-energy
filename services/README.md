# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Grid Assets → Dispatch → Grid Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `grid-asset-service` | 8081 | IEC 61970 CIM asset concepts |
| `dispatch-service` | 8082 | Operations capacity discipline |
| `grid-rules-service` | 8083 | Operating envelopes as auditable rules |
| `meter-catalog-service` | 8084 | Metering asset data |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/grid-asset-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
