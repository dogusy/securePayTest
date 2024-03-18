package com.securepay.securepay.repos;

import com.securepay.securepay.entities.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

    List<Transaction> findByUserId(Integer customerId);

    List<Transaction> findByCardNumber(String cardNumber);

    Page<Transaction> findByCardNumberAndDateBetween(String cardNumber, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
