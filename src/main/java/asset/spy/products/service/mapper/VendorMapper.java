package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.http.vendor.CreateVendorDto;
import asset.spy.products.service.dto.http.vendor.ResponseVendorDto;
import asset.spy.products.service.dto.http.vendor.UpdateVendorDto;
import asset.spy.products.service.entity.VendorEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.OffsetDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    VendorEntity toVendorEntity(CreateVendorDto vendorDTO);

    @Mapping(source = "externalId", target = "id")
    ResponseVendorDto toResponseVendorDto(VendorEntity vendor);

    @Mapping(target = "id", ignore = true)
    void updateVendor(UpdateVendorDto vendorDto, @MappingTarget VendorEntity vendorEntity);

    @AfterMapping
    default void setExternalId(@MappingTarget VendorEntity vendor) {
        if (vendor.getId() == null) {
            vendor.setExternalId(UUID.randomUUID());
        }
    }

    @AfterMapping
    default void setCreatedAt(@MappingTarget VendorEntity vendor) {
        if (vendor.getCreatedAt() == null) {
            vendor.setCreatedAt(OffsetDateTime.now());
        }
    }
}