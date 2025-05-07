package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.kafka.ProductItemDto;
import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.entity.ProductItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    @Mapping(target = "id", source = "productItemDto.itemId")
    @Mapping(target = "product", source = "productEntity")
    ProductItemEntity toSpecificProductEntity (ProductItemDto productItemDto, ProductEntity productEntity);
}
