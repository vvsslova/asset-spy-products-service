package asset.spy.products.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"asset.spy.products.service", "asset.spy.auth.lib"})
public class AssetSpyProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetSpyProductsApplication.class, args);
    }
}
