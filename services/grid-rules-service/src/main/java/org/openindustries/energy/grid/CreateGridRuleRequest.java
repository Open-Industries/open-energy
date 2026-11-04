package org.openindustries.energy.grid;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateGridRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "URBAN|RURAL|INDUSTRIAL|ISLAND") String zoneType,
        @Positive long maxLoadMw,
        @Positive int maxOutageMinutes,
        @Min(1) @Max(100) int priority) {
}
