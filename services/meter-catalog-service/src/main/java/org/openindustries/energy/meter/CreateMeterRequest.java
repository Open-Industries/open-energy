package org.openindustries.energy.meter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateMeterRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "SMART|INDUSTRIAL|LEGACY") String meterType,
        @Positive int readingsPerDay) {
}
