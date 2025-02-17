package kz.netcracker.libra.event;

import kz.netcracker.libra.config.properties.AppKafkaProperties;
import kz.netcracker.libra.dto.BookDto;
import kz.netcracker.libra.mapper.BookEventMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookEventPublisher {
    private final KafkaTemplate<String, BookEvent> kafkaTemplate;
    private final BookEventMapper eventMapper;
    private final AppKafkaProperties kafkaProperties;

    public void publishEvent(EventType type, BookDto bookDto) {
        BookEvent event = eventMapper.toEvent(bookDto);
        
        // Use book's ISBN as key for consistent partitioning
        String key = bookDto.getIsbn();

        ProducerRecord<String, BookEvent> record = new ProducerRecord<>(
            kafkaProperties.getTopics().getBookEvents().getName(),
            key,  // messages with same key go to the same partition
            event  // value
        );

        // Add headers/metadata
        record.headers()
            .add("eventType", type.name().getBytes())
            .add("timestamp", String.valueOf(System.currentTimeMillis()).getBytes())
            .add("source", "libra-service".getBytes());

        kafkaTemplate.send(record);
    }
}