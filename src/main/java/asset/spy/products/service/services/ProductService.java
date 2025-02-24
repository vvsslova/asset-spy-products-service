package asset.spy.products.service.services;

import asset.spy.products.service.dto.ProductDTO;
import asset.spy.products.service.entity.Product;
import asset.spy.products.service.entity.Vendor;
import asset.spy.products.service.mapper.ProductMapper;
import asset.spy.products.service.repositories.ProductRepository;
import asset.spy.products.service.repositories.VendorRepository;
import asset.spy.products.service.util.hashing.IDHasher;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final ProductMapper productMapper;

    public List<ProductDTO> getProducts(int page, int size) {
        log.info("Get products from page {} of size {}", page, size);

        return productRepository
                .findAll(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProduct(long id) {
        log.info("Get product with id {}", id);

        Product product = productRepository
                .findById(IDHasher.hashId(id))
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return productMapper.toProductDTO(product);
    }

    public ProductDTO saveProduct(ProductDTO product, long vendorId) {
        log.info("Received product to save : {}", product);

        Vendor vendor = vendorRepository
                .findById(IDHasher.hashId(vendorId))
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        log.info("Founded vendor to save : {}", vendor);

        Product productToSave = Product.builder()
                .name(product.getName())
                .type(product.getType())
                .manufacturer(product.getManufacturer())
                .description(product.getDescription())
                .price(product.getPrice())
                .vendor(vendor)
                .build();
        log.info("Product to save : {}", productToSave);

        productRepository.save(productToSave);
        return productMapper.toProductDTO(productToSave);
    }

    public ProductDTO updateProduct(ProductDTO product) {
        log.info("Received product to update : {}", product);

        Product productToUpdate = productRepository
                .findById(IDHasher.hashId(product.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        productToUpdate.setName(product.getName());
        productToUpdate.setType(product.getType());
        productToUpdate.setManufacturer(product.getManufacturer());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        log.info("Product to update : {}", productToUpdate);

        return productMapper.toProductDTO(productToUpdate);
    }

    public String deleteProduct(long id) {
        log.info("Received id to delete : {}", id);

        Optional<Product> productToDelete = productRepository.findById(IDHasher.hashId(id));
        if (productToDelete.isEmpty()) {
            throw new EntityNotFoundException("Product with id " + id + "not found");
        }

        productRepository.deleteById(id);
        log.info("Product with id {} was deleted", id);
        return "Product with id " + id + " deleted";
    }

    public List<ProductDTO> getProductsByVendorId(long id) {
        log.info("Get products by vendor with id {}", id);

        Vendor vendor = vendorRepository
                .findById(IDHasher.hashId(id))
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        log.info("Founded vendor to get products : {}", vendor);

        return vendor
                .getProducts()
                .stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }
}