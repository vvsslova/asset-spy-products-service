package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.ResponseVendorDto;
import asset.spy.products.service.dto.SaveVendorDto;
import asset.spy.products.service.dto.UpdateVendorDto;
import asset.spy.products.service.entity.VendorEntity;
import asset.spy.products.service.util.hashing.IDHasher;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    VendorEntity toVendorEntity(SaveVendorDto vendorDTO);

    @Mapping(source = "id", target = "id", qualifiedByName = "hashId")
    ResponseVendorDto toResponseVendorDto(VendorEntity vendor);

    default void updateVendor(VendorEntity vendor, UpdateVendorDto vendorDto) {
        vendor.setName(vendorDto.getName() == null ? vendor.getName() : vendorDto.getName());
        vendor.setCountry(vendorDto.getCountry() == null ? vendor.getCountry() : vendorDto.getCountry());
    }

    @Named("hashId")
    default Long hashId(Long id) {
        return IDHasher.hashId(id);
    }

    @AfterMapping
    default void setCreatedAt(@MappingTarget VendorEntity vendor) {
        vendor.setCreatedAt(OffsetDateTime.now());
    }
}