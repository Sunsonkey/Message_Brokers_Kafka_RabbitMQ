package ru.netology.creditprocessingservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.netology.creditprocessingservice.event.CreditApplicationEvent;
import ru.netology.creditprocessingservice.service.CreditProcessingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditApplicationConsumer {

    private final CreditProcessingService processingService;

    @KafkaListener(topics = "${application.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCreditApplication(CreditApplicationEvent event) {
        log.info("Received credit application: {}", event);

        try {
            processingService.processCreditApplication(event);
        } catch (Exception e) {
            log.error("Error processing credit application: {}", event, e);
        }
    }
}