package com.securepay.securepay.controller;

import com.securepay.securepay.entities.CreditCard;
import com.securepay.securepay.entities.User;
import com.securepay.securepay.entities.request.CreateCreditCardRequest;
import com.securepay.securepay.entities.response.CreateCreditCardResponse;
import com.securepay.securepay.repos.CreditCardRepository;
import com.securepay.securepay.service.CardService;
import com.securepay.securepay.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CardServiceTest {
    private  CustomerService customerService;
    private  CreditCardRepository creditCardRepository;

    private CardService cardService;

    @BeforeEach
    public void setUp() {
        customerService = mock(CustomerService.class);
        creditCardRepository = mock(CreditCardRepository.class);
        cardService = new CardService(customerService, creditCardRepository);
    }

    @Test
    public void whenCreateCreditCardRequest_thenCreateCreditCard() {
        CreateCreditCardRequest request = CreateCreditCardRequest.builder()
                .userId(1)
                .build();
        var user = User.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("test")
                .id(1)
                .build();
        var creditCard = CreditCard.builder()
                .id(1)
                .cardNumber("1234567890")
                .user(user)
                .build();
        CreateCreditCardResponse response = CreateCreditCardResponse.builder()
                .success(true)
                .message("Card created successfully")
                .creditCard(creditCard)
                .build();

        Mockito.when(customerService.findUserByUserId(request.getUserId())).thenReturn(user);
        Mockito.when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        assertEquals(response.isSuccess(), cardService.createCreditCard(request).isSuccess());
    }

    @Test
    public void whenCreateCreditCardRequest_thenCreateCreditCardWithEmptyUserId() {
        CreateCreditCardRequest request = CreateCreditCardRequest.builder()
                .userId(null)
                .build();
        CreateCreditCardResponse response = CreateCreditCardResponse.builder()
                .success(false)
                .message("Empty User Id")
                .build();
        assertEquals(response.isSuccess(), cardService.createCreditCard(request).isSuccess());
    }

    @Test
    public void whenFindCreditCardByCardNumber_thenFindCreditCard() {
        var user = User.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("test")
                .id(1)
                .build();
        var creditCard = CreditCard.builder()
                .id(1)
                .cardNumber("1234567890")
                .user(user)
                .build();
        Mockito.when(creditCardRepository.findByCardNumber(creditCard.getCardNumber())).thenReturn(creditCard);
        assertEquals(creditCard, cardService.findByCardNumber(creditCard.getCardNumber()));
    }
}
