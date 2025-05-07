package asset.spy.products.service.dto.http.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {

    @NotNull(message = "Article cannot be empty")
    private Long article;

    @NotNull(message = "Name cannot be empty")
    @Size(max = 200, message = "Field cannot be greater than 200 characters")
    private String name;

    @NotNull(message = "Type cannot be empty!")
    @Size(max = 200, message = "Field cannot be greater than 200 characters")
    private String type;

    @NotNull(message = "Manufacturer cannot be empty!")
    @Size(max = 200, message = "Field cannot be greater than 200 characters")
    private String manufacturer;

    @NotNull(message = "Description cannot be empty!")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0.00")
    @DecimalMax(value = "99999999.99", message = "Price must be less than or equal to 99999999.99")
    @Digits(integer = 8, fraction = 2, message = "Price must have up to 8 integer digits and 2 decimal places")
    private BigDecimal price;
}