package asset.spy.products.service.controllers;

import asset.spy.products.service.dto.ProductDTO;
import asset.spy.products.service.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public List<ProductDTO> getProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return productService.getProducts(page, size);
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/save/vendor_id/{vendor_id}")
    public ProductDTO saveProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable long vendor_id) {
        return productService.saveProduct(productDTO, vendor_id);
    }

    @PutMapping("/update")
    public ProductDTO updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}