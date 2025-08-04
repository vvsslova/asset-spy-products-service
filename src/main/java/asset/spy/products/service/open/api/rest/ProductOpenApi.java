package asset.spy.products.service.open.api.rest;

import asset.spy.products.service.dto.http.ErrorResponseDto;
import asset.spy.products.service.dto.http.product.CreateProductDto;
import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.product.UpdateProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

@Tag(name = "Products", description = "Product API")
@SecurityRequirement(name = "jwt-bearer")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = "application/json", schema =
                @Schema(implementation = ErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema =
                @Schema(implementation = ErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "403", description = "Access denied", content = {
                @Content(mediaType = "application/json", schema =
                @Schema(implementation = ErrorResponseDto.class))
        })
})
public interface ProductOpenApi {

    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "OK")
    Page<ResponseProductDto> getProducts(@RequestParam(defaultValue = "0") @Parameter(
                                                 description = "number of page") int page,
                                         @RequestParam(defaultValue = "10") @Parameter(
                                                 description = "count elements on page") int size,
                                         @RequestParam(required = false, defaultValue = "id") @Parameter(
                                                 description = "criteria of sorting") String sortCriteria,
                                         @RequestParam(required = false) @Parameter(
                                                 description = "filtration by name") String name,
                                         @RequestParam(required = false) @Parameter(
                                                 description = "filtration by type") String type,
                                         @RequestParam(required = false) @Parameter(
                                                 description = "filtration by manufacturer") String manufacturer,
                                         @RequestParam(required = false) @Parameter(
                                                 description = "filtration by minPrice") BigDecimal minPrice,
                                         @RequestParam(required = false) @Parameter(
                                                 description = "filtration by maxPrice") BigDecimal maxPrice);

    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseProductDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Product not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    ResponseProductDto getProduct(@PathVariable @Parameter(description = "Article of product") Long article);

    @Operation(summary = "Save product by vendor ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseProductDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vendor not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    ResponseProductDto saveProduct(@Valid @RequestBody CreateProductDto productDto,
                                   @PathVariable @Parameter(description = "ID of vendor") UUID vendorId);

    @Operation(summary = "Update product info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseProductDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Product not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    ResponseProductDto updateProduct(@Valid @RequestBody UpdateProductDto productDto);

    @Operation(summary = "Delete product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK - Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    void deleteProduct(@PathVariable @Parameter(description = "Article of product") Long article);
}
