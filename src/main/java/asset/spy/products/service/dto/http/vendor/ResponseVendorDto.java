package asset.spy.products.service.dto.http.vendor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for getting vendor")
public class ResponseVendorDto {

    @Schema(description = "ID of vendor", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;

    @Schema(description = "Name of vendor", example = "Some name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Country of vendor", example = "Some country", requiredMode = Schema.RequiredMode.REQUIRED)
    private String country;
}