package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.ResponseProductDto;
import asset.spy.products.service.dto.SaveProductDto;
import asset.spy.products.service.dto.UpdateProductDto;
import asset.spy.products.service.entity.ProductEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    ProductEntity toProduct(SaveProductDto productDto);

    @Mapping(source = "externalId", target = "id")
    ResponseProductDto toResponseProductDto(ProductEntity product);

    @Mapping(target = "id", ignore = true)
    void updateProduct(UpdateProductDto productDto, @MappingTarget ProductEntity product);

    @AfterMapping
    default void setExternalId(@MappingTarget ProductEntity product) {
        if (product.getExternalId() == null) {
            product.setExternalId(UUID.randomUUID());
        }
    }
}