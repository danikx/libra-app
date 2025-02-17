package kz.netcracker.libra.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.kafka")
public class AppKafkaProperties {
    private Topics topics = new Topics();

    @Getter
    @Setter
    public static class Topics {
        private TopicConfig bookEvents = new TopicConfig();
    }

    @Getter
    @Setter
    public static class TopicConfig {
        private String name;
        private Integer partitions;
        private Integer replicas;
    }
}