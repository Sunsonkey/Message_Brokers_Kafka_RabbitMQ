package ru.netology.creditapplicationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditResponseEvent {
    private Long applicationId;
    private String status;
    private String message;
}