package org.openindustries.energy.grid;

import java.util.UUID;

/**
 * GridRule aggregate — a decision rule of this core: for one zoneType, it guarantees a
 * capacity (maxLoadMw) and a threshold (maxOutageMinutes), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class GridRule {
    public UUID id;
    public String name;
    public String zoneType;
    public long maxLoadMw;
    public int maxOutageMinutes;
    public int priority;
    public String status;

    public static GridRule create(String name, String zoneType, long maxLoadMw, int maxOutageMinutes, int priority) {
        GridRule r = new GridRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.zoneType = zoneType;
        r.maxLoadMw = maxLoadMw;
        r.maxOutageMinutes = maxOutageMinutes;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
