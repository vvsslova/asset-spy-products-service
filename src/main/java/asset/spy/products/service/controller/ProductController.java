package asset.spy.products.service.controller;

import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.product.CreateProductDto;
import asset.spy.products.service.dto.http.product.UpdateProductDto;
import asset.spy.products.service.open.api.rest.ProductOpenApi;
import asset.spy.products.service.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@AllArgsConstructor
public class ProductController implements ProductOpenApi {
    private final ProductService productService;

    @GetMapping()
    public Page<ResponseProductDto> getProducts(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(required = false, defaultValue = "id") String sortCriteria,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String type,
                                                @RequestParam(required = false) String manufacturer,
                                                @RequestParam(required = false) BigDecimal minPrice,
                                                @RequestParam(required = false) BigDecimal maxPrice) {
        return productService.getProducts(page, size, sortCriteria, name, type, manufacturer, maxPrice, minPrice);
    }

    @GetMapping("/{article}")
    public ResponseProductDto getProduct(@PathVariable Long article) {
        return productService.getProduct(article);
    }

    @PostMapping("/save/{vendorId}")
    public ResponseProductDto saveProduct(@Valid @RequestBody CreateProductDto productDto, @PathVariable UUID vendorId) {
        return productService.saveProduct(productDto, vendorId);
    }

    @PutMapping("/update")
    public ResponseProductDto updateProduct(@Valid @RequestBody UpdateProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/{article}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long article) {
        productService.deleteProduct(article);
    }
}