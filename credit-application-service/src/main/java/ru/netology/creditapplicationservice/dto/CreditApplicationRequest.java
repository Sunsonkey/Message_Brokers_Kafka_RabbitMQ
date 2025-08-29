package ru.netology.creditapplicationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationRequest {
    private BigDecimal amount;
    private Integer termMonths;
    private BigDecimal income;
    private BigDecimal currentDebt;
    private Integer creditRating;
}