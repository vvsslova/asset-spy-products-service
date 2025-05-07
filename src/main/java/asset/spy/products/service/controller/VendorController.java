package asset.spy.products.service.controller;

import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.vendor.ResponseVendorDto;
import asset.spy.products.service.dto.http.vendor.CreateVendorDto;
import asset.spy.products.service.dto.http.vendor.UpdateVendorDto;
import asset.spy.products.service.service.ProductService;
import asset.spy.products.service.service.VendorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
import java.util.UUID;

@RestController
@RequestMapping("/v1/vendors")
@AllArgsConstructor
public class VendorController {
    private final VendorService vendorService;
    private final ProductService productService;

    @GetMapping()
    public Page<ResponseVendorDto> getVendors(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(required = false, defaultValue = "id") String sortCriteria,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String country) {
        return vendorService.getVendors(page, size, sortCriteria, name, country);
    }

    @GetMapping("/{id}")
    public ResponseVendorDto getVendorById(@PathVariable UUID id) {
        return vendorService.getVendorById(id);
    }

    @PostMapping("/save")
    public ResponseVendorDto save(@Valid @RequestBody CreateVendorDto vendorDto) {
        return vendorService.saveVendor(vendorDto);
    }

    @PutMapping("/update")
    public ResponseVendorDto updateVendor(@Valid @RequestBody UpdateVendorDto vendorDto) {
        return vendorService.updateVendor(vendorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseVendorDto deleteVendor(@PathVariable UUID id) {
        return vendorService.deleteVendor(id);
    }

    @GetMapping("/{id}/products")
    public List<ResponseProductDto> getVendorProducts(@PathVariable UUID id) {
        return productService.getProductsByVendorId(id);
    }
}