package asset.spy.products.service.kafka.service;

import asset.spy.products.service.kafka.config.KafkaProperties;
import asset.spy.products.service.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorTopicService {

    private final VendorRepository vendorRepository;
    private final AdminClient adminClient;
    private final KafkaProperties kafkaProperties;

    public List<String> getDynamicTopics() {
        List<String> vendorNames = vendorRepository.findAllVendorNames();

        return vendorNames.stream()
                .map(String::toLowerCase)
                .map(kafkaProperties.getVendorTopicPrefix()::concat)
                .map(vendorName -> vendorName.replace(" ", "_"))
                .toList();
    }

    public void updateVendorTopics(List<String> actualVendorTopics) {
        try {
            Set<String> alreadyExistsTopics = adminClient.listTopics().names().get();
            Set<String> alreadyExistsVendorTopics = getVendorTopics(alreadyExistsTopics);

            deleteUnusableVendorTopics(alreadyExistsVendorTopics, actualVendorTopics);

            createNotExistingVendorTopics(alreadyExistsVendorTopics, actualVendorTopics);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void deleteUnusableVendorTopics(Set<String> alreadyExistsVendorTopics, List<String> actualVendorTopics) {
        Collection<String> unusedTopicsToDelete = new ArrayList<>();
        for (String vendorTopic : alreadyExistsVendorTopics) {
            if (!actualVendorTopics.contains(vendorTopic)) {
                unusedTopicsToDelete.add(vendorTopic);
            }
        }

        adminClient.deleteTopics(unusedTopicsToDelete);
    }

    private void createNotExistingVendorTopics(Set<String> alreadyExistsVendorTopics, List<String> actualVendorTopics) {
        Collection<NewTopic> topicsToCreate = new ArrayList<>();
        for (String topic : actualVendorTopics) {
            if (!alreadyExistsVendorTopics.contains(topic)) {
                NewTopic newTopic = TopicBuilder
                        .name(topic)
                        .partitions(kafkaProperties.getCountPartitions())
                        .build();
                topicsToCreate.add(newTopic);
            }
        }
        adminClient.createTopics(topicsToCreate);
    }

    private Set<String> getVendorTopics(Set<String> allTopics) {
        return allTopics.stream()
                .filter(topic -> topic.startsWith(kafkaProperties.getVendorTopicPrefix()))
                .collect(Collectors.toSet());
    }
}
