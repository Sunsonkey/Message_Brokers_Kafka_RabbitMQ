package ru.netology.creditprocessingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.netology.creditprocessingservice.event.CreditApplicationEvent;
import ru.netology.creditprocessingservice.event.CreditResponseEvent;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditProcessingService {

    private final RabbitTemplate rabbitTemplate;

    public void processCreditApplication(CreditApplicationEvent event) {
        log.info("Processing credit application: {}", event);

        boolean approved = isCreditApproved(
                event.getAmount(),
                event.getTermMonths(),
                event.getIncome(),
                event.getCurrentDebt(),
                event.getCreditRating()
        );

        CreditResponseEvent response = new CreditResponseEvent();
        response.setApplicationId(event.getApplicationId());

        if (approved) {
            response.setStatus("APPROVED");
            response.setMessage("Credit application approved");
        } else {
            response.setStatus("REJECTED");
            response.setMessage("Credit application rejected");
        }

        rabbitTemplate.convertAndSend(
                "credit-exchange",
                "credit.response",
                response
        );

        log.info("Sent credit response: {}", response);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, Integer termMonths) {
        if (termMonths == 0) {
            return BigDecimal.ZERO;
        }
        return amount.divide(new BigDecimal(termMonths), 2, BigDecimal.ROUND_HALF_UP);
    }

    private boolean isCreditApproved(BigDecimal amount, Integer termMonths,
                                     BigDecimal income, BigDecimal currentDebt,
                                     Integer creditRating) {

        BigDecimal monthlyPayment = calculateMonthlyPayment(amount, termMonths);

        BigDecimal maxAllowedPayment = income.multiply(new BigDecimal("0.5"));

        BigDecimal currentMonthlyDebt = currentDebt.divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);

        BigDecimal totalMonthlyObligations = monthlyPayment.add(currentMonthlyDebt);

        boolean paymentWithinLimit = totalMonthlyObligations.compareTo(maxAllowedPayment) <= 0;
        boolean goodCreditRating = creditRating >= 600;

        return paymentWithinLimit && goodCreditRating;
    }
}