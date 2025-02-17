package kz.netcracker.libra.config;

import kz.netcracker.libra.config.properties.AppKafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final AppKafkaProperties kafkaProperties;

    @Bean
    public NewTopic bookEventsTopic() {
        return TopicBuilder
                .name(kafkaProperties.getTopics().getBookEvents().getName())
                .partitions(kafkaProperties.getTopics().getBookEvents().getPartitions())
                .replicas(kafkaProperties.getTopics().getBookEvents().getReplicas())
                .build();
    }
}