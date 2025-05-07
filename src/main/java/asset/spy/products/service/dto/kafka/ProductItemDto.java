package asset.spy.products.service.dto.kafka;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ProductItemDto {
    private Long article;
    private UUID itemId;
    private String productStatus;
    private OffsetDateTime timestamp;
}
