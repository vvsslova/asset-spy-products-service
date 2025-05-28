package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.kafka.ProductItemDto;
import asset.spy.products.service.entity.ProductItemEntity;
import asset.spy.products.service.entity.ProductItemStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductItemStatusMapper {

    @Mapping(target = "status", source = "productItemDto.productStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productItem", source = "productItemEntity")
    @Mapping(target = "statusTransitionTime", source = "productItemDto.timestamp")
    ProductItemStatusEntity toEntity (ProductItemDto productItemDto, ProductItemEntity productItemEntity);
}
