package asset.spy.products.service.dto.http.vendor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for creating vendor")
public class CreateVendorDto {

    @NotNull(message = "Name cannot be empty")
    @Size(max = 200, message = "Field cannot be greater than 200 characters")
    @Schema(description = "Name of vendor", example = "Some name")
    private String name;

    @NotNull(message = "Country cannot be empty")
    @Size(max = 200, message = "Field cannot be greater than 200 characters")
    @Schema(description = "Country of vendor", example = "Some country")
    private String country;
}