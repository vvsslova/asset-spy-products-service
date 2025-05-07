package asset.spy.products.service.kafka.config;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaProperties {
    private Integer countPartitions;
    private String vendorTopicPrefix;
    private String bootstrapServers;
    private String listenerPrefix;
    private Integer concurrency;
    private Map<String, Object> consumerProperties;

    @Nullable
    public String getGroupId() {
        return consumerProperties.containsKey("group.id")
                ? consumerProperties.get("group.id").toString()
                : null;
    }
}
