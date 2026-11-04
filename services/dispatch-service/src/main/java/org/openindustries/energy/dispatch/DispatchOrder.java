package org.openindustries.energy.dispatch;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a DispatchCapacity. */
public class DispatchOrder {
    public UUID id;
    public UUID accountId;
    public String sourceType;
    public long mw;
    public long remainingAfter;
    public Instant recordedAt;

    public static DispatchOrder of(UUID accountId, String sourceType, long mw, long remainingAfter) {
        DispatchOrder r = new DispatchOrder();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.sourceType = sourceType;
        r.mw = mw;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
