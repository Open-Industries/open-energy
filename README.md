# Open Energy — Reference Energy Core Platform

A **standard, generic energy and utilities core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> dispatched energy against an asset's available capacity never exceeds that capacity — a dispatch order that would overrun it is rejected (422) and records nothing.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| IEC 61850 | Substation automation & data models | Asset data posture; protocol stays in substation systems |
| IEC 61970/61968 (CIM) | Energy management information model | `docs/domain/domain-standards.md` concept mapping |
| IEEE 1547 | DER interconnection | Boundary: interconnection compliance stays with certified equipment |
| OpenADR / IEC 62746-10-1 | Demand response signals | Boundary: DR programs integrate at the edge |
| IEC 62443 | OT/ICS security | Security zones posture in `docs/security/` |
| ISO 50001 | Energy management | Governance and life-cycle docs |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `grid-asset-service` | 8081 | IEC 61970 CIM asset concepts | Registry of grid assets (CIM concept-level shapes). |
| `dispatch-service` | 8082 | Operations capacity discipline | Guarded dispatch capacity per asset and the dispatch orders against it. |
| `grid-rules-service` | 8083 | Operating envelopes as auditable rules | Decision rules binding zone types to load and outage envelopes. |
| `meter-catalog-service` | 8084 | Metering asset data | Metering point catalogue. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `grid-anomaly-scorer` | Event / HTTP | Scores grid deviation signals (voltage, frequency, losses) with an auditable rationale. |
| `reliability-evaluator` | Event / HTTP | Compares outage minutes and availability against asset targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — dispatched energy against an asset's available capacity never exceeds that capacity — a dispatch order that would overrun it is rejected (422) and records nothing.
3. **Privacy by design** — Grid operational data is critical-infrastructure information: it is treated as sensitive operational data (IEC 62443 zones/conduits posture), never mixed with customer personal data, and all values here are synthetic.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/grid-asset-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `grid-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `dispatch-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
