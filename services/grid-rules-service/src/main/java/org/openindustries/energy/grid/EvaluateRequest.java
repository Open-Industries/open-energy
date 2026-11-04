package org.openindustries.energy.grid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "URBAN|RURAL|INDUSTRIAL|ISLAND") String zoneType,
        @Positive int requiredMaxOutageMinutes,
        @Positive long requiredMaxLoadMw) {
}
