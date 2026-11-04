package org.openindustries.energy.dispatch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateDispatchOrderRequest(
        @NotBlank @Pattern(regexp = "SOLAR|WIND|BATTERY|GRID") String sourceType,
        @Positive long mw) {
}
