# Standards Traceability Matrix

Every standard or framework claimed by this platform and where its evidence lives.
"Aligned to" throughout — no certification or regulatory-compliance claim is made.

| Standard / framework | Area | Evidence |
|---|---|---|
| ISO/IEC/IEEE 42010 | Architecture description | `docs/architecture/architecture-description.md`; `docs/adr/` |
| ISO/IEC 25010 | Quality model | `docs/architecture/quality-attributes.md` (QS-01…QS-08); tests; SLOs |
| ISO/IEC/IEEE 15288 / 12207 | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC 27001 / 27002 | ISMS controls | `docs/security/` |
| Twelve-Factor App | Cloud methodology | `docs/architecture/twelve-factor.md` |
| OpenAPI 3.1 / AsyncAPI 2.6 / CloudEvents 1.0 | Contracts & events | `contracts/` |
| RFC 9457 | Error format | `Problem.java` in every service |
| IEC 61850 | Substation automation & data models | Asset data posture; protocol stays in substation systems |
| IEC 61970/61968 (CIM) | Energy management information model | `docs/domain/domain-standards.md` concept mapping |
| IEEE 1547 | DER interconnection | Boundary: interconnection compliance stays with certified equipment |
| OpenADR / IEC 62746-10-1 | Demand response signals | Boundary: DR programs integrate at the edge |
| IEC 62443 | OT/ICS security | Security zones posture in `docs/security/` |
| ISO 50001 | Energy management | Governance and life-cycle docs |
