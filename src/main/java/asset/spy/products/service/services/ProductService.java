package asset.spy.products.service.services;

import asset.spy.products.service.dto.ResponseProductDto;
import asset.spy.products.service.dto.SaveProductDto;
import asset.spy.products.service.dto.UpdateProductDto;
import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.entity.VendorEntity;
import asset.spy.products.service.mapper.ProductMapper;
import asset.spy.products.service.repositories.ProductRepository;
import asset.spy.products.service.repositories.VendorRepository;
import asset.spy.products.service.util.hashing.IDHasher;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ResponseProductDto> getProducts(int page, int size, String sortCriteria) {

        log.info("Get products from page {} of size {}", page, size);

        return productRepository
                .findAll(PageRequest.of(page, size, Sort.by(sortCriteria)))
                .map(productMapper::toResponseProductDto);
    }

    @Transactional(readOnly = true)
    public ResponseProductDto getProduct(long id) {
        log.info("Start getting product with id {}", id);

        ProductEntity product = findProductById(id);

        return productMapper.toResponseProductDto(product);
    }

    @Transactional
    public ResponseProductDto saveProduct(SaveProductDto product, long vendorId) {
        log.info("Received product to save : {}", product);

        VendorEntity vendor = findVendorById(vendorId);
        log.info("Founded vendor to save : {}", vendor);

        ProductEntity productToSave = productMapper.toProduct(product);
        productToSave.setVendor(vendor);
        log.info("Product to save : {}", productToSave);

        productRepository.save(productToSave);
        return productMapper.toResponseProductDto(productToSave);
    }

    @Transactional
    public ResponseProductDto updateProduct(UpdateProductDto productDto) {
        log.info("Received product to update : {}", productDto);

        ProductEntity productToUpdate = findProductById(productDto.getId());

        productMapper.updateProduct(productToUpdate, productDto);
        log.info("Product to update : {}", productToUpdate);

        return productMapper.toResponseProductDto(productToUpdate);
    }

    @Transactional
    public ResponseProductDto deleteProduct(long id) {
        log.info("Received id to delete : {}", id);

        ProductEntity product = findProductById(id);

        productRepository.delete(product);
        log.info("Product with id {} was deleted", id);
        return productMapper.toResponseProductDto(product);
    }

    @Transactional(readOnly = true)
    public List<ResponseProductDto> getProductsByVendorId(long id) {
        log.info("Get products by vendor with id {}", id);

        VendorEntity vendor = findVendorById(id);
        log.info("Founded vendor to get products : {}", vendor);

        return vendor
                .getProducts()
                .stream()
                .map(productMapper::toResponseProductDto)
                .collect(Collectors.toList());
    }

    private VendorEntity findVendorById(long id) {
        log.info("Getting vendor with id {}", id);
        return vendorRepository
                .findById(IDHasher.hashId(id))
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
    }

    private ProductEntity findProductById(long id) {
        log.info("Getting product with id {}", id);
        return productRepository
                .findById(IDHasher.hashId(id))
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }
}