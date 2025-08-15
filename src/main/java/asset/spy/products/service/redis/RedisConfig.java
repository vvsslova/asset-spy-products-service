package asset.spy.products.service.redis;

import asset.spy.products.service.dto.http.product.ResponseProductDto;
import asset.spy.products.service.dto.http.vendor.ResponseVendorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.cache.redis.time-to-live}")
    private Duration defaultKeyTimeToLive;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                          RedisSerializer<ResponseVendorDto> vendorSerializer,
                                          RedisSerializer<ResponseProductDto> productSerializer,
                                          ObjectMapper objectMapper) {

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        cacheConfigurations.put("vendorConfig", createTypeCacheConfig(vendorSerializer, defaultKeyTimeToLive));
        cacheConfigurations.put("productConfig", createTypeCacheConfig(productSerializer, defaultKeyTimeToLive));

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
                .entryTtl(defaultKeyTimeToLive)
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    @Bean
    public RedisSerializer<ResponseVendorDto> responseVendorDtoSerializer(ObjectMapper objectMapper) {
        return new Jackson2JsonRedisSerializer<>(objectMapper, ResponseVendorDto.class);
    }

    @Bean
    public RedisSerializer<ResponseProductDto> responseProductDtoSerializer(ObjectMapper objectMapper) {
        return new Jackson2JsonRedisSerializer<>(objectMapper, ResponseProductDto.class);
    }

    private RedisCacheConfiguration createTypeCacheConfig(RedisSerializer<?> serializer, Duration keyTimeToLive) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .entryTtl(keyTimeToLive)
                .disableCachingNullValues();
    }
}
