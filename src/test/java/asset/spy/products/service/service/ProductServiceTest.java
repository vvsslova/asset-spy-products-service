package asset.spy.products.service.service;

import static org.assertj.core.api.Assertions.assertThat;

import asset.spy.products.service.AbstractInitialization;
import asset.spy.products.service.dto.http.product.CreateProductDto;
import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.mapper.ProductMapper;
import asset.spy.products.service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest extends AbstractInitialization {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private ProductService productService;

    @Mock
    private SpecificationCreateService specificationCreateService;

    @Test
    void saveProductIfVendorExistsTest() {
        when(vendorService.findVendorById(vendorId)).thenReturn(vendor);
        when(productMapper.toProduct(createProductDto, PRODUCT_ARTICLE)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.getProductArticle()).thenReturn(PRODUCT_ARTICLE);
        when(productMapper.toResponseProductDto(product)).thenReturn(responseProductDto);

        ResponseProductDto result = productService.saveProduct(createProductDto, vendorId);

        assertThat(result).isEqualTo(responseProductDto);
        verify(vendorService).findVendorById(vendorId);
        verify(productMapper).toProduct(createProductDto, PRODUCT_ARTICLE);
        verify(productRepository).save(product);
        verify(productMapper).toResponseProductDto(product);
    }

    @Test
    void saveProductIfVendorNotExistTest() {
        when(vendorService.findVendorById(vendorId)).thenThrow(new EntityNotFoundException("Vendor not found"));

        assertThrows(EntityNotFoundException.class, () -> productService.saveProduct(createProductDto, vendorId));
        verify(vendorService).findVendorById(vendorId);
    }

    @Test
    void updateProductIfProductExistsTest() {
        when(productRepository.findByArticle(PRODUCT_ARTICLE)).thenReturn(Optional.of(product));
        when(productMapper.toResponseProductDto(product)).thenReturn(responseProductDto);

        ResponseProductDto result = productService.updateProduct(updateProductDto);

        assertThat(result).isEqualTo(responseProductDto);
        verify(productRepository).findByArticle(PRODUCT_ARTICLE);
        verify(productMapper).toResponseProductDto(product);
    }

    @Test
    void updateProductIfProductNotExistsTest() {
        when(productRepository.findByArticle(PRODUCT_ARTICLE)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(updateProductDto));
        verify(productRepository).findByArticle(PRODUCT_ARTICLE);
    }

    @Test
    void deleteProductIfProductExistsTest() {
        when(productRepository.findByArticle(PRODUCT_ARTICLE)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(PRODUCT_ARTICLE);

        verify(productRepository).findByArticle(PRODUCT_ARTICLE);
        verify(productRepository).delete(product);
    }

    @Test
    void deleteProductIfProductNotExistsTest() {
        when(productRepository.findByArticle(PRODUCT_ARTICLE)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(PRODUCT_ARTICLE));
        verify(productRepository).findByArticle(PRODUCT_ARTICLE);
        verifyNoMoreInteractions(productRepository, productMapper);
    }

    @Test
    void getProductsByVendorIfVendorExistsTest() {
        List<ProductEntity> products = Collections.singletonList(product);
        vendor.setProducts(products);
        when(vendorService.findVendorById(vendorId)).thenReturn(vendor);
        when(productMapper.toResponseProductDto(product)).thenReturn(responseProductDto);

        List<ResponseProductDto> result = productService.getProductsByVendorId(vendorId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(responseProductDto);
        verify(vendorService).findVendorById(vendorId);
        verify(productMapper).toResponseProductDto(product);
    }

    @Test
    void getProductsByVendorIfVendorNotExistsTest() {
        when(vendorService.findVendorById(vendorId)).thenThrow(new EntityNotFoundException("Vendor not found"));

        assertThrows(EntityNotFoundException.class, () -> productService.getProductsByVendorId(vendorId));

        verify(vendorService).findVendorById(vendorId);
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    void getProductsByVendorIfVendorHasNoProductsTest() {
        when(vendorService.findVendorById(vendorId)).thenReturn(vendor);
        vendor.setProducts(Collections.emptyList());
        List<ResponseProductDto> result = productService.getProductsByVendorId(vendorId);

        assertThat(result).isEmpty();

        verify(vendorService).findVendorById(vendorId);
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    void getProductByExternalIdIfProductExistsTest() {
        when(productRepository.findByArticle(PRODUCT_ARTICLE)).thenReturn(Optional.of(product));
        when(productMapper.toResponseProductDto(product)).thenReturn(responseProductDto);

        ResponseProductDto result = productService.getProduct(PRODUCT_ARTICLE);

        assertThat(result).isEqualTo(responseProductDto);
        verify(productRepository).findByArticle(PRODUCT_ARTICLE);
        verify(productMapper).toResponseProductDto(product);
    }

    @Test
    void getProductByExternalIdIfProductNotExistsTest() {
        when(productRepository.findByArticle(PRODUCT_ARTICLE)).thenReturn(Optional.empty());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, ()
                -> productService.getProduct(PRODUCT_ARTICLE));

        assertEquals("Product not found", e.getMessage());
        verify(productRepository).findByArticle(PRODUCT_ARTICLE);
        verify(productMapper, never()).toResponseProductDto(any());
    }

    @Test
    void getProductsWithoutFiltersTest() {
        Page<ProductEntity> productPage = new PageImpl<>(Collections.singletonList(product),
                PageRequest.of(PAGE, SIZE, Sort.by(SORT_CRITERIA)), 1);
        Specification<ProductEntity> productSpecification = Specification.where(null);

        when(specificationCreateService.getProductSpecification(null, null, null, null, null))
                .thenReturn(productSpecification);
        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productPage);
        when(productMapper.toResponseProductDto(product)).thenReturn(responseProductDto);

        Page<ResponseProductDto> result = productService.getProducts(PAGE, SIZE, SORT_CRITERIA,
                null, null, null, null, null);

        assertNotNull(result);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0)).isEqualTo(responseProductDto);

        verify(specificationCreateService).getProductSpecification(null, null, null, null, null);
        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(productMapper).toResponseProductDto(product);
    }

    @Test
    void getProductsWithFiltersTest() {
        Page<ProductEntity> productPage = new PageImpl<>(Collections.singletonList(product),
                PageRequest.of(PAGE, SIZE, Sort.by(SORT_CRITERIA)), 1);
        Specification<ProductEntity> productSpecification = Specification.where(null);

        when(specificationCreateService.getProductSpecification(NAME, TYPE, MANUFACTURER, MIN_PRICE, MAX_PRICE))
                .thenReturn(productSpecification);
        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productPage);
        when(productMapper.toResponseProductDto(product)).thenReturn(responseProductDto);

        Page<ResponseProductDto> result = productService.getProducts(PAGE, SIZE, SORT_CRITERIA,
                NAME, TYPE, MANUFACTURER, MAX_PRICE, MIN_PRICE);

        assertNotNull(result);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0)).isEqualTo(responseProductDto);

        verify(specificationCreateService).getProductSpecification(NAME, TYPE, MANUFACTURER, MIN_PRICE, MAX_PRICE);
        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(productMapper).toResponseProductDto(product);
    }
}