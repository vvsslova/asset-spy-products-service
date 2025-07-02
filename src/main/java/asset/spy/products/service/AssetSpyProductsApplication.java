package asset.spy.products.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"asset.spy.products.service", "asset.spy.auth.lib"},
        exclude = org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class)
@EnableCaching
public class AssetSpyProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetSpyProductsApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper (){
        return new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
