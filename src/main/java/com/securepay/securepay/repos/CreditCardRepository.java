package com.securepay.securepay.repos;

import com.securepay.securepay.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    CreditCard findByCardNumber(String cardNumber);
}
