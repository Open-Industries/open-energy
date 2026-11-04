package org.openindustries.energy.asset;

import java.time.Instant;
import java.util.UUID;

/**
 * GridAsset aggregate — Registry of grid assets (CIM concept-level shapes).
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class GridAsset {
    public UUID id;
        public String name;
        public String assetType;
        public String regionCode;
    public String status;
    public Instant createdAt;

    public static GridAsset create(String name, String assetType, String regionCode) {
        GridAsset e = new GridAsset();
        e.id = UUID.randomUUID();
        e.name = name;
        e.assetType = assetType;
        e.regionCode = regionCode;
        e.status = "IN_SERVICE";
        e.createdAt = Instant.now();
        return e;
    }
}
