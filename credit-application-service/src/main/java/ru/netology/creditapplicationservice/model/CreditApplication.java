package ru.netology.creditapplicationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(nullable = false)
    private BigDecimal income;

    @Column(name = "current_debt", nullable = false)
    private BigDecimal currentDebt;

    @Column(name = "credit_rating", nullable = false)
    private Integer creditRating;

    @Column(nullable = false)
    private String status = "IN_PROCESS";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}