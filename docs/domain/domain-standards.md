# Domain Standards — Energy

Alignment mapping for this core. *Aligned to* throughout: no protection, control or
interconnection certification is claimed.

## Concept mapping

| Platform concept | Domain standard | Mapping |
|---|---|---|
| Grid assets | IEC 61970 CIM (concept level) / IEC 61850 (substation level) | Asset registry shapes follow CIM asset concepts; substation protocols stay in OT systems |
| Dispatch | Market/operations practice | Guarded capacity accounts; the no-overrun invariant |
| Demand response | OpenADR / IEC 62746-10-1 | DR signals integrate at the boundary as events |
| DER interconnection | IEEE 1547 | Compliance is certified at equipment level; this core records assets and capacity only |
| OT security | IEC 62443 | Zones-and-conduits posture in the security architecture |
| Energy management | ISO 50001 | Governance documents |

## Scope boundaries

This core never operates protection relays, SCADA control or real-time balancing. It is
the enterprise/operations-data plane around certified OT systems.
