package ru.netology.creditapplicationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationEvent {
    private Long applicationId;
    private BigDecimal amount;
    private Integer termMonths;
    private BigDecimal income;
    private BigDecimal currentDebt;
    private Integer creditRating;
}