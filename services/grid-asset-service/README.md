# Grid Asset Service

Bounded context: **Grid Assets** — Registry of grid assets (CIM concept-level shapes).

Alignment: IEC 61970 CIM asset concepts. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/grid-assets` |
| Owns data | GridAsset records |
| Publishes | `asset.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/grid-asset-service quarkus:dev`.
