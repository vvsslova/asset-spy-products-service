package asset.spy.products.service.service;

import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.entity.VendorEntity;
import asset.spy.products.service.specification.ProductSpecification;
import asset.spy.products.service.specification.VendorSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SpecificationCreateService {

    public Specification<VendorEntity> getVendorSpecification(String name, String country) {
        return Specification
                .where(VendorSpecification.hasName(name))
                .and(VendorSpecification.hasCountry(country));
    }

    public Specification<ProductEntity> getProductSpecification(String name, String type,
                                                                String manufacturer, BigDecimal minPrice,
                                                                BigDecimal maxPrice) {
        return Specification
                .where(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasType(type))
                .and(ProductSpecification.hasManufacturer(manufacturer))
                .and(ProductSpecification.minPrice(minPrice))
                .or(ProductSpecification.maxPrice(maxPrice));
    }
}