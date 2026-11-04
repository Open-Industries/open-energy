package org.openindustries.energy.meter;

import java.util.UUID;

/** Meter aggregate — Metering point catalogue. */
public class Meter {
    public UUID id;
        public String name;
        public String meterType;
        public int readingsPerDay;
    public String status;

    public static Meter create(String name, String meterType, int readingsPerDay) {
        Meter e = new Meter();
        e.id = UUID.randomUUID();
        e.name = name;
        e.meterType = meterType;
        e.readingsPerDay = readingsPerDay;
        e.status = "INSTALLED";
        return e;
    }
}
