package asset.spy.products.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVendorDto {

    @NotNull(message = "Id cannot be empty")
    private UUID id;

    @NotNull(message = "Name cannot be empty")
    @Size(max = 200, message = "Field cannot be greater than 200 characters")
    private String name;

    @NotNull(message = "Country cannot be empty")
    @Size(max = 200, message = "Field cannot be greater than 200 characters")
    private String country;
}