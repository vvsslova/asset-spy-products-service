package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.product.CreateProductDto;
import asset.spy.products.service.dto.http.product.UpdateProductDto;
import asset.spy.products.service.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "article", source = "productArticle")
    ProductEntity toProduct(CreateProductDto productDto, Long productArticle);

    ResponseProductDto toResponseProductDto(ProductEntity product);

    @Mapping(target = "id", ignore = true)
    void updateProduct(UpdateProductDto productDto, @MappingTarget ProductEntity product);
}