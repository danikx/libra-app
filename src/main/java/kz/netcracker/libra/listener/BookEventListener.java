package kz.netcracker.libra.listener;

import kz.netcracker.libra.event.model.BookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookEventListener {

    @KafkaListener(
            topics = "${app.kafka.topics.book-events.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            concurrency = "${app.kafka.topics.book-events.concurrency}"
    )
    public void handleBookCreated(ConsumerRecord<String, BookEvent> record) {
        BookEvent event = record.value();

        String eventType = new String(record.headers().lastHeader("eventType").value());
        String timestamp = new String(record.headers().lastHeader("timestamp").value());

        log.info("Received book created event: {}, type: {}, timestamp: {}", event, eventType, timestamp);

        // some business logic here

    }
}