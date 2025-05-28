package asset.spy.products.service.kafka.config;

import asset.spy.products.service.dto.kafka.ProductItemDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class ProductItemConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, ProductItemDto> consumerFactory() {

        return new DefaultKafkaConsumerFactory<>(kafkaProperties.getConsumerProperties(), new StringDeserializer(),
                new JsonDeserializer<>(ProductItemDto.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductItemDto> kafkaListenerContainerFactory(ConsumerFactory<String, ProductItemDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, ProductItemDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(kafkaProperties.getConcurrency());

        return factory;
    }
}
