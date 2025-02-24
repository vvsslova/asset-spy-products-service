package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.VendorDTO;
import asset.spy.products.service.entity.Vendor;
import asset.spy.products.service.util.hashing.IDHasher;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "hashId")
    VendorDTO toVendorDTO(Vendor vendor);

    @Mapping(target = "createdAt", ignore = true)
    Vendor toVendor(VendorDTO vendorDTO);

    @Named("hashId")
    default Long hashId(Long id) {
        return IDHasher.hashId(id);
    }

    @AfterMapping
    default void setCreatedAt(@MappingTarget Vendor vendor) {
        vendor.setCreatedAt(OffsetDateTime.now());
    }
}
