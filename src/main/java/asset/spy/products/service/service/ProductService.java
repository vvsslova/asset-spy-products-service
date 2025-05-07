package asset.spy.products.service.service;

import asset.spy.products.service.dto.kafka.ProductItemDto;
import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.product.CreateProductDto;
import asset.spy.products.service.dto.http.product.UpdateProductDto;
import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.entity.ProductItemEntity;
import asset.spy.products.service.entity.ProductItemStatusEntity;
import asset.spy.products.service.entity.VendorEntity;
import asset.spy.products.service.mapper.ProductItemMapper;
import asset.spy.products.service.mapper.ProductItemStatusMapper;
import asset.spy.products.service.mapper.ProductMapper;
import asset.spy.products.service.repository.ProductRepository;
import asset.spy.products.service.repository.ProductItemRepository;
import asset.spy.products.service.repository.ProductItemStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final VendorService vendorService;
    private final ProductMapper productMapper;
    private final SpecificationCreateService specificationCreateService;
    private final ProductItemRepository productItemRepository;
    private final ProductItemStatusRepository productItemStatusRepository;
    private final ProductItemStatusMapper productItemStatusMapper;
    private final ProductItemMapper productItemMapper;

    @Transactional(readOnly = true)
    public Page<ResponseProductDto> getProducts(int page, int size, String sortCriteria,
                                                String name, String type, String manufacturer,
                                                BigDecimal maxPrice, BigDecimal minPrice) {

        log.info("Get products from page {} of size {}", page, size);

        Specification<ProductEntity> specification = specificationCreateService
                .getProductSpecification(name, type, manufacturer, minPrice, maxPrice);
        return productRepository
                .findAll(specification, PageRequest.of(page, size, Sort.by(sortCriteria)))
                .map(productMapper::toResponseProductDto);
    }

    @Transactional(readOnly = true)
    public ResponseProductDto getProduct(Long article) {
        log.info("Start getting product with article {}", article);

        ProductEntity product = findProductByArticle(article);

        return productMapper.toResponseProductDto(product);
    }

    @Transactional
    public ResponseProductDto saveProduct(CreateProductDto product, UUID vendorId) {
        log.info("Received product to save : {}", product);

        VendorEntity vendor = vendorService.findVendorById(vendorId);
        log.info("Founded vendor to save : {}", vendor);

        ProductEntity productToSave = productMapper.toProduct(product, productRepository.getProductArticle());
        productToSave.setVendor(vendor);
        log.info("Product to save : {}", productToSave);

        return productMapper.toResponseProductDto(productRepository.save(productToSave));
    }

    @Transactional
    public ResponseProductDto updateProduct(UpdateProductDto productDto) {
        log.info("Received product to update : {}", productDto);

        ProductEntity productToUpdate = findProductByArticle(productDto.getArticle());

        productMapper.updateProduct(productDto, productToUpdate);
        log.info("Product to update : {}", productToUpdate);

        return productMapper.toResponseProductDto(productToUpdate);
    }

    @Transactional
    public ResponseProductDto deleteProduct(Long article) {
        log.info("Received article to delete : {}", article);

        ProductEntity product = findProductByArticle(article);

        productRepository.delete(product);
        log.info("Product with article {} was deleted", article);
        return productMapper.toResponseProductDto(product);
    }

    @Transactional(readOnly = true)
    public List<ResponseProductDto> getProductsByVendorId(UUID id) {
        log.info("Get products by vendor with id {}", id);

        VendorEntity vendor = vendorService.findVendorById(id);
        log.info("Founded vendor to get products : {}", vendor);

        return vendor
                .getProducts()
                .stream()
                .map(productMapper::toResponseProductDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void accountProduct(ProductItemDto message) {

        log.info("Received emulator message to accounting product with article : {}", message.getArticle());
        Optional<ProductEntity> productEntity = productRepository.findByArticle(message.getArticle());
        if (productEntity.isEmpty()) {
            throw new EntityNotFoundException("Product with article " + message.getArticle() + " not found");
        }

        Optional<ProductItemEntity> productItem = productItemRepository.findById(message.getItemId());
        if (productItem.isPresent()) {
            log.info("Adding new status for product item with id: {}", message.getItemId());

            ProductItemEntity foundedProductItemEntity = productItem.get();
            productItemStatusRepository.save(productItemStatusMapper.toEntity(message, foundedProductItemEntity));

            log.info("Adding new status for product item was successful");
        } else {
            log.info("Adding product item with id: {} and his status", message.getItemId());

            ProductEntity foundedProductEntity = productEntity.get();
            ProductItemEntity productItemToSave = productItemMapper.toSpecificProductEntity(message, foundedProductEntity);
            productItemRepository.save(productItemToSave);
            ProductItemStatusEntity status = productItemStatusMapper.toEntity(message, productItemToSave);
            productItemStatusRepository.save(status);

            log.info("Adding product item and his status was successful");
        }
    }

    private ProductEntity findProductByArticle(Long article) {
        log.info("Getting product with article {}", article);
        return productRepository
                .findByArticle(article)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }
}