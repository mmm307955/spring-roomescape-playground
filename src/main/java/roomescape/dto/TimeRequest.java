package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record TimeRequest(
    @NotNull
    LocalTime time
) {
}
