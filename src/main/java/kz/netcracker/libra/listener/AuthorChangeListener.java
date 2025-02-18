package kz.netcracker.libra.listener;

import kz.netcracker.libra.event.model.AuthorChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorChangeListener {
    
    @KafkaListener(
        topics = "${app.kafka.topics.author-events.name}",
        groupId = "${app.kafka.topics.author-events.groupId}",
        concurrency = "${app.kafka.topics.author-events.concurrency}"
    )
    public void handleAuthorChange(AuthorChangeEvent event) {
        log.info("Received author change: {}", event);
        
        switch (event.getPayload().getOp()) {
            case "c" -> handleCreate(event);
            case "u" -> handleUpdate(event);
            case "d" -> handleDelete(event);
        }
    }
    
    private void handleCreate(AuthorChangeEvent event) {
        var author = event.getPayload().getAfter();
        log.info("Author created: {}", author);
    }
    
    private void handleUpdate(AuthorChangeEvent event) {
        var before = event.getPayload().getBefore();
        var after = event.getPayload().getAfter();
        log.info("Author updated from {} to {}", before, after);
    }
    
    private void handleDelete(AuthorChangeEvent event) {
        var author = event.getPayload().getBefore();
        log.info("Author deleted: {}", author);
    }
}