package asset.spy.products.service.mapper;

import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.product.CreateProductDto;
import asset.spy.products.service.dto.http.product.UpdateProductDto;
import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.AbstractInitialization;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductMapperTest extends AbstractInitialization {

    private final ProductMapper productMapper = new ProductMapperImpl();

    @Test
    void saveProductDtoToProductEntityTest() {
        CreateProductDto productDto = createProductDto;

        ProductEntity productEntity = productMapper.toProduct(productDto, PRODUCT_ARTICLE);

        assertThat(productDto)
                .usingRecursiveComparison()
                .isEqualTo(productEntity);
    }

    @Test
    void productEntityToResponseProductDtoTest() {
        ResponseProductDto productDto = productMapper.toResponseProductDto(product);

        assertThat(productDto)
                .usingRecursiveComparison()
                .ignoringFields("id", "vendor")
                .isEqualTo(product);
    }

    @Test
    void updateProductDtoToProductEntityTest() {
        UpdateProductDto productDto = updateProductDto;
        ProductEntity productEntity = product;

        productMapper.updateProduct(productDto, productEntity);

        assertThat(productDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(productEntity);
    }
}
