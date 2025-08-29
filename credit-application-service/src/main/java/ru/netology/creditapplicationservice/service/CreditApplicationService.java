package ru.netology.creditapplicationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.creditapplicationservice.dto.CreditApplicationRequest;
import ru.netology.creditapplicationservice.event.CreditApplicationEvent;
import ru.netology.creditapplicationservice.model.CreditApplication;
import ru.netology.creditapplicationservice.repository.CreditApplicationRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final CreditApplicationRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Long createApplication(CreditApplicationRequest request) {
        CreditApplication application = new CreditApplication();
        application.setAmount(request.getAmount());
        application.setTermMonths(request.getTermMonths());
        application.setIncome(request.getIncome());
        application.setCurrentDebt(request.getCurrentDebt());
        application.setCreditRating(request.getCreditRating());

        CreditApplication savedApplication = repository.save(application);

        CreditApplicationEvent event = new CreditApplicationEvent(
                savedApplication.getId(),
                savedApplication.getAmount(),
                savedApplication.getTermMonths(),
                savedApplication.getIncome(),
                savedApplication.getCurrentDebt(),
                savedApplication.getCreditRating()
        );

        kafkaTemplate.send("credit-applications", event);

        return savedApplication.getId();
    }

    public String getApplicationStatus(Long applicationId) {
        return repository.findById(applicationId)
                .map(CreditApplication::getStatus)
                .orElse("NOT_FOUND");
    }

    @Transactional
    public void updateApplicationStatus(Long applicationId, String status) {
        repository.findById(applicationId).ifPresent(application -> {
            application.setStatus(status);
            repository.save(application);
        });
    }
}