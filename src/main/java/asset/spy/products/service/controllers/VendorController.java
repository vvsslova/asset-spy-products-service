package asset.spy.products.service.controllers;

import asset.spy.products.service.dto.ProductDTO;
import asset.spy.products.service.dto.VendorDTO;
import asset.spy.products.service.services.ProductService;
import asset.spy.products.service.services.VendorService;
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
@RequestMapping("/api/vendors")
@AllArgsConstructor
public class VendorController {
    private final VendorService vendorService;
    private final ProductService productService;

    @GetMapping()
    public List<VendorDTO> getVendors(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return vendorService.getVendors(page, size);
    }

    @GetMapping("/{id}")
    public VendorDTO getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @PostMapping("/save")
    public VendorDTO save(@Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendor(vendorDTO);
    }

    @PutMapping("/update")
    public VendorDTO updateVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateVendor(vendorDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        return vendorService.deleteVendor(id);
    }

    @GetMapping("/{id}/products")
    public List<ProductDTO> getVendorProducts(@PathVariable Long id) {
        return productService.getProductsByVendorId(id);
    }
}
