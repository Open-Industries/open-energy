package org.openindustries.energy.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateGridAssetRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "SUBSTATION|FEEDER|TRANSFORMER|SOLAR_PLANT|BATTERY") String assetType,
        @NotBlank String regionCode) {
}
