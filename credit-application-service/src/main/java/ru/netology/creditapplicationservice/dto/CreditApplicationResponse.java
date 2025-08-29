package ru.netology.creditapplicationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationResponse {
    private Long applicationId;
    private String status;
}