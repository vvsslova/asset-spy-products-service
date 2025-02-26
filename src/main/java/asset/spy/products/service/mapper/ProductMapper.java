package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.ResponseProductDto;
import asset.spy.products.service.dto.SaveProductDto;
import asset.spy.products.service.dto.UpdateProductDto;
import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.util.hashing.IDHasher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    ProductEntity toProduct(SaveProductDto productDto);

    @Mapping(source = "id", target = "id", qualifiedByName = "hashId")
    ResponseProductDto toResponseProductDto(ProductEntity product);

    default void updateProduct(ProductEntity product, UpdateProductDto productDto) {
        product.setName(productDto.getName() == null ? product.getName() : productDto.getName());
        product.setDescription(productDto.getDescription() == null ? product.getDescription() : productDto.getDescription());
        product.setPrice(productDto.getPrice() == null ? product.getPrice() : productDto.getPrice());
        product.setType(productDto.getType() == null ? product.getType() : productDto.getType());
        product.setManufacturer(productDto.getManufacturer() == null ? product.getManufacturer() : productDto.getManufacturer());
    }

    @Named("hashId")
    default Long hashId(Long id) {
        return IDHasher.hashId(id);
    }
}