package ru.netology.creditapplicationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.creditapplicationservice.model.CreditApplication;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {
}