package asset.spy.products.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ErrorResponseDTO {
    private String message;
    private OffsetDateTime time;

    public ErrorResponseDTO(String message) {
        this.message = message;
        this.time = OffsetDateTime.now();
    }
}
