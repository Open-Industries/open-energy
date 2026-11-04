package org.openindustries.energy.dispatch;

import java.util.UUID;

/**
 * DispatchCapacity aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class DispatchCapacity {
    public UUID id;
        public java.util.UUID assetId;
    public long capacityMw;
    public String status;

    public static DispatchCapacity open(java.util.UUID assetId, long capacityMw) {
        DispatchCapacity a = new DispatchCapacity();
        a.id = UUID.randomUUID();
        a.assetId = assetId;
        a.capacityMw = capacityMw;
        a.status = "ACTIVE";
        return a;
    }
}
