package asset.spy.products.service.open.api.rest;

import asset.spy.products.service.dto.http.ErrorResponseDto;
import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.vendor.CreateVendorDto;
import asset.spy.products.service.dto.http.vendor.ResponseVendorDto;
import asset.spy.products.service.dto.http.vendor.UpdateVendorDto;
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

import java.util.List;
import java.util.UUID;

@Tag(name = "Vendors", description = "Vendor API")
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
public interface VendorOpenApi {

    @Operation(summary = "Get all vendors with filters and sorting")
    @ApiResponse(responseCode = "200", description = "OK")
    Page<ResponseVendorDto> getVendors(@RequestParam(defaultValue = "0") @Parameter(
                                               description = "number of page") int page,
                                       @RequestParam(defaultValue = "10") @Parameter(
                                               description = "count elements on page") int size,
                                       @RequestParam(required = false, defaultValue = "id") @Parameter(
                                               description = "criteria of sorting") String sortCriteria,
                                       @RequestParam(required = false) @Parameter(
                                               description = "filtration by name") String name,
                                       @RequestParam(required = false) @Parameter(
                                               description = "filtration by country") String country);

    @Operation(summary = "Get vendor by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseVendorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vendor not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    ResponseVendorDto getVendorById(@PathVariable @Parameter(description = "ID of vendor") UUID id);

    @Operation(summary = "Save vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseVendorDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "Vendor already exists", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    ResponseVendorDto save(@Valid @RequestBody CreateVendorDto vendorDto);

    @Operation(summary = "Update vendor info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseVendorDto.class))
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
    ResponseVendorDto updateVendor(@Valid @RequestBody UpdateVendorDto vendorDto);

    @Operation(summary = "Delete vendor by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK - Vendor deleted"),
            @ApiResponse(responseCode = "404", description = "Vendor not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    void deleteVendor(@PathVariable @Parameter(description = "ID of vendor") UUID id);

    @Operation(summary = "Get all vendor products by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseProductDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vendor not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))
            })
    })
    List<ResponseProductDto> getVendorProducts(@PathVariable @Parameter(description = "ID of vendor") UUID id);
}
