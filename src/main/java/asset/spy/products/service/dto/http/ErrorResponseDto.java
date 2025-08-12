package asset.spy.products.service.dto.http;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Schema(description = "DTO for service errors")
public class ErrorResponseDto {

    @Schema(description = "Error message", example = "Some error message", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(description = "Error time", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime time;

    public ErrorResponseDto(String message) {
        this.message = message;
        this.time = OffsetDateTime.now();
    }
}