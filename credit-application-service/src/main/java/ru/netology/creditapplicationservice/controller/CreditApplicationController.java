package ru.netology.creditapplicationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.creditapplicationservice.dto.CreditApplicationRequest;
import ru.netology.creditapplicationservice.dto.CreditApplicationResponse;
import ru.netology.creditapplicationservice.service.CreditApplicationService;

@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<CreditApplicationResponse> applyForCredit(
            @RequestBody CreditApplicationRequest request) {

        Long applicationId = applicationService.createApplication(request);

        CreditApplicationResponse response = new CreditApplicationResponse(
                applicationId,
                "IN_PROCESS"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{applicationId}")
    public ResponseEntity<CreditApplicationResponse> getApplicationStatus(
            @PathVariable Long applicationId) {

        String status = applicationService.getApplicationStatus(applicationId);

        CreditApplicationResponse response = new CreditApplicationResponse(
                applicationId,
                status
        );

        return ResponseEntity.ok(response);
    }
}