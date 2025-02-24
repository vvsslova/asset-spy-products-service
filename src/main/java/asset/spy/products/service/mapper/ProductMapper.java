package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.ProductDTO;
import asset.spy.products.service.entity.Product;
import asset.spy.products.service.util.hashing.IDHasher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "hashId")
    ProductDTO toProductDTO(Product product);

    @Named("hashId")
    default Long hashId(Long id) {
        return IDHasher.hashId(id);
    }
}
