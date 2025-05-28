package asset.spy.products.service;

import asset.spy.products.service.dto.http.product.CreateProductDto;
import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.vendor.ResponseVendorDto;
import asset.spy.products.service.dto.http.vendor.CreateVendorDto;
import asset.spy.products.service.dto.http.product.UpdateProductDto;
import asset.spy.products.service.dto.http.vendor.UpdateVendorDto;
import asset.spy.products.service.entity.ProductEntity;
import asset.spy.products.service.entity.VendorEntity;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class AbstractInitialization {

    protected static final int PAGE = 0;
    protected static final int SIZE = 10;
    protected static final String SORT_CRITERIA = "name";
    protected static final String NAME = "Test Vendor";
    protected static final String COUNTRY = "Country";
    protected static final String TYPE = "Type";
    protected static final String MANUFACTURER = "Manufacturer";
    protected static final BigDecimal MAX_PRICE = BigDecimal.valueOf(100);
    protected static final BigDecimal MIN_PRICE = BigDecimal.valueOf(50);
    protected static final Long PRODUCT_ARTICLE = 1000000L;

    protected static UUID vendorId;

    protected static ProductEntity product;
    protected static VendorEntity vendor;
    protected static ResponseProductDto responseProductDto;
    protected static UpdateProductDto updateProductDto;
    protected static CreateProductDto createProductDto;
    protected static CreateVendorDto createVendorDto;
    protected static UpdateVendorDto updateVendorDto;
    protected static ResponseVendorDto responseVendorDto;

    @BeforeAll
    public static void init() {
        vendorId = UUID.randomUUID();

        product = createProduct();
        vendor = createVendor();
        createProductDto = createSaveProductDto();
        updateProductDto = createUpdateProductDto();
        responseProductDto = createResponseProductDto();
        createVendorDto = createSaveVendorDto();
        updateVendorDto = createUpdateVendorDto();
        responseVendorDto = createResponseVendorDto();
    }

    private static ProductEntity createProduct() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setType("Test Type");
        product.setManufacturer("Test Manufacturer");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100));
        product.setArticle(PRODUCT_ARTICLE);
        return product;
    }

    private static VendorEntity createVendor() {
        VendorEntity vendor = new VendorEntity();
        vendor.setExternalId(vendorId);
        vendor.setName("Test Vendor");
        vendor.setCountry("Test Country");
        return vendor;
    }

    private static CreateProductDto createSaveProductDto() {
        CreateProductDto dto = new CreateProductDto();
        dto.setName("Test Product");
        dto.setDescription("Test Description");
        dto.setType("Test Type");
        dto.setManufacturer("Test Manufacturer");
        dto.setPrice(BigDecimal.valueOf(100));
        return dto;
    }

    private static UpdateProductDto createUpdateProductDto() {
        UpdateProductDto dto = new UpdateProductDto();
        dto.setName("Updated Product Name");
        dto.setDescription("Updated Product Description");
        dto.setType("Updated Product Type");
        dto.setManufacturer("Updated Manufacturer");
        dto.setPrice(BigDecimal.valueOf(80));
        dto.setArticle(PRODUCT_ARTICLE);
        return dto;
    }

    private static ResponseProductDto createResponseProductDto() {
        ResponseProductDto dto = new ResponseProductDto();
        dto.setArticle(PRODUCT_ARTICLE);
        dto.setName("Test Product");
        dto.setDescription("Test Description");
        dto.setType("Test Type");
        dto.setManufacturer("Test Manufacturer");
        dto.setPrice(BigDecimal.valueOf(100));
        return dto;
    }

    private static CreateVendorDto createSaveVendorDto() {
        CreateVendorDto dto = new CreateVendorDto();
        dto.setName("Test Vendor Name");
        dto.setCountry("Test Country");
        return dto;
    }

    private static UpdateVendorDto createUpdateVendorDto() {
        UpdateVendorDto dto = new UpdateVendorDto();
        dto.setName("Updated Vendor Name");
        dto.setCountry("Updated Country");
        return dto;
    }

    private static ResponseVendorDto createResponseVendorDto() {
        ResponseVendorDto dto = new ResponseVendorDto();
        dto.setId(vendorId);
        dto.setName("Test Vendor Name");
        dto.setCountry("Test Country");
        return dto;
    }
}
