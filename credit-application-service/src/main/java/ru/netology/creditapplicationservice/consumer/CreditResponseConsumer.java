package ru.netology.creditapplicationservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.netology.creditapplicationservice.event.CreditResponseEvent;
import ru.netology.creditapplicationservice.service.CreditApplicationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditResponseConsumer {

    private final CreditApplicationService applicationService;

    @RabbitListener(queues = "${application.rabbitmq.queue}")
    public void receiveCreditResponse(CreditResponseEvent event) {
        log.info("Received credit response: {}", event);

        applicationService.updateApplicationStatus(
                event.getApplicationId(),
                event.getStatus()
        );

        log.info("Application {} status updated to {}",
                event.getApplicationId(), event.getStatus());
    }
}