package asset.spy.products.service.dto.http.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for getting product")
public class ResponseProductDto {

    @Schema(description = "Article of product", example = "1000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long article;

    @Schema(description = "Name of product", example = "Some name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Type of product", example = "Some type", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "Manufacturer of product", example = "Some manufacturer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String manufacturer;

    @Schema(description = "Description of product", example = "Some description", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Price of product", example = "0.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;
}