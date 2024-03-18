package com.securepay.securepay.service;

import com.securepay.securepay.entities.CreditCard;
import com.securepay.securepay.entities.request.CreateCreditCardRequest;
import com.securepay.securepay.entities.response.CreateCreditCardResponse;
import com.securepay.securepay.repos.CreditCardRepository;
import com.securepay.securepay.utils.card.CardHelper;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CustomerService customerService;
    private final CreditCardRepository creditCardRepository;

    public CardService(CustomerService customerService, CreditCardRepository creditCardRepository) {
        this.customerService = customerService;
        this.creditCardRepository = creditCardRepository;
    }
    public CreateCreditCardResponse createCreditCard(CreateCreditCardRequest request) {
        try {
            if (request.getUserId() == null) {
                return CreateCreditCardResponse.builder()
                        .success(false)
                        .message("Empty User Id")
                        .build();
            }
            var user = customerService.findUserByUserId(request.getUserId());
            if (user == null) {
                return CreateCreditCardResponse.builder()
                        .success(false)
                        .message("Invalid user")
                        .build();
            }

            var creditCard = new CreditCard();
            String cardNumber = this.generateUniqueCardNumber();

            creditCard.setCardNumber(cardNumber);
            creditCard.setUser(user);
            creditCardRepository.save(creditCard);
            return CreateCreditCardResponse.builder()
                    .success(true)
                    .message("Card created successfully")
                    .creditCard(creditCard)
                    .build();
        }catch (Exception e){
            return CreateCreditCardResponse.builder()
                    .success(false)
                    .message("Something has happened")
                    .build();
        }

    }

    public CreditCard findByCardNumber(String cardNumber) {
        try {
            return this.creditCardRepository.findByCardNumber(cardNumber);
        }catch (Exception e){
            return null;
        }
    }

    private String generateUniqueCardNumber(){
        boolean isUnique = false;
        String cardNumber ="";
        while (!isUnique){
            cardNumber = CardHelper.generateCardNumber();
            isUnique = creditCardRepository.findByCardNumber(cardNumber) == null;
        }
        return cardNumber;
    }

}
