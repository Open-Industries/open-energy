# Meter Catalog Service

Bounded context: **Meters** — Metering point catalogue.

Alignment: Metering asset data. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/meters` |
| Owns data | Meter records |

Run locally: `mvn -pl services/meter-catalog-service quarkus:dev`.
