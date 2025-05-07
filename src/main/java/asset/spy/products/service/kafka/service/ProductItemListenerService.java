package asset.spy.products.service.kafka.service;

import asset.spy.products.service.dto.kafka.ProductItemDto;
import asset.spy.products.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductItemListenerService implements MessageListener<String, ProductItemDto> {
    private final ProductService productService;

    @Override
    public void onMessage(ConsumerRecord<String, ProductItemDto> data) {
        try {
            productService.accountProduct(data.value());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
