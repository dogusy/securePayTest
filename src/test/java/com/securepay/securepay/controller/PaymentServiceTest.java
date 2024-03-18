package com.securepay.securepay.controller;

import com.securepay.securepay.auth.JwtService;
import com.securepay.securepay.entities.CreditCard;
import com.securepay.securepay.entities.Transaction;
import com.securepay.securepay.entities.User;
import com.securepay.securepay.entities.request.MakePaymentRequest;
import com.securepay.securepay.entities.request.QueryPaymentRequest;
import com.securepay.securepay.entities.response.MakePaymentResponse;
import com.securepay.securepay.entities.response.PaymentsResponse;
import com.securepay.securepay.repos.TransactionRepository;
import com.securepay.securepay.repos.UserRepository;
import com.securepay.securepay.service.CardService;
import com.securepay.securepay.service.CustomerService;
import com.securepay.securepay.service.PaymentService;
import com.securepay.securepay.utils.card.DateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PaymentServiceTest {
    private TransactionRepository transactionRepository;
    private CustomerService customerService;
    private CardService cardService;

    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        customerService = mock(CustomerService.class);
        cardService = mock(CardService.class);
        paymentService = new PaymentService(transactionRepository, customerService, cardService);
    }

    @Test
    public void whenQueryPaymentsByCustomerId_thenReturnPaymentsResponse() {
        Integer customerId = 1;
        PaymentsResponse paymentsResponse=PaymentsResponse.builder()
                .transactionList(new ArrayList<>())
                .success(true)
                .message("")
                .build();
        Mockito.when(transactionRepository.findByUserId(customerId)).thenReturn(new ArrayList<>());
        var result = paymentService.queryPaymentsByCustomerId(customerId);
        assertEquals(result.isSuccess(),paymentsResponse.isSuccess());
        Mockito.verify(transactionRepository, Mockito.times(1)).findByUserId(customerId);
    }

    @Test
    public void whenQueryPaymentsByCardNumber_thenReturnPaymentsResponse() {
        String cardNumber = "1234567890123456";
        PaymentsResponse paymentsResponse=PaymentsResponse.builder()
                .transactionList(new ArrayList<>())
                .success(true)
                .message("")
                .build();
        Mockito.when(transactionRepository.findByCardNumber(cardNumber)).thenReturn(new ArrayList<>());
        var result = paymentService.queryPaymentsByCardNumber(cardNumber);
        assertEquals(result.isSuccess(),paymentsResponse.isSuccess());
        Mockito.verify(transactionRepository, Mockito.times(1)).findByCardNumber(cardNumber);
    }


    @Test
    public void whenQueryPaymentsByByCustomerEmail_thenReturnPaymentsResponse() {
        String customerEmail = "test@gmail.com";
        PaymentsResponse paymentsResponse=PaymentsResponse.builder()
                .transactionList(new ArrayList<>())
                .success(true)
                .message("")
                .build();
        var user = User.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("1234")
                .build();
        Mockito.when(customerService.findUserByEmail(customerEmail)).thenReturn(user);
        Mockito.when(transactionRepository.findByUserId(user.getId())).thenReturn(new ArrayList<>());
        var result = paymentService.queryPaymentsByCustomerEmail(customerEmail);
        assertEquals(result.isSuccess(),paymentsResponse.isSuccess());
        Mockito.verify(customerService, Mockito.times(1)).findUserByEmail(customerEmail);
        Mockito.verify(transactionRepository, Mockito.times(1)).findByUserId(user.getId());

        }


    @Test
    public void whenQueryPaymentsByStartDateAndEndDate_thenReturnPaymentsResponse() {
        String customerEmail = "test@gmail.com";
        var request= QueryPaymentRequest.builder()
                .endDate("01-02-2024")
                .startDate("01-01-2024")
                .cardNumber("1234567890123456")
                .size(10)
                .page(0)
                .build();
        var paymentsResponse=PaymentsResponse.builder()
                .transactionList(new ArrayList<>())
                .success(true)
                .message("")
                .build();
        LocalDateTime startDate = DateHelper.convertStringToLocalDateTime(request.getStartDate());
        LocalDateTime endDate = DateHelper.convertStringToLocalDateTime(request.getEndDate()).plusDays(1);
        Pageable paging = PageRequest.of(request.getPage(), request.getSize());

        Mockito.when(transactionRepository.findByCardNumberAndDateBetween(request.getCardNumber(), startDate, endDate,paging)).thenReturn(Page.empty());
        var result = paymentService.queryPaymentsByStartDateAndEndDate(request,request.getPage(),request.getSize());
        assertEquals(result.isSuccess(),paymentsResponse.isSuccess());
        Mockito.verify(transactionRepository, Mockito.times(1)).findByCardNumberAndDateBetween(request.getCardNumber(), startDate, endDate,paging);
        }
    @Test
    public void whenMakePayment_thenReturnPaymentsResponse() {
        String cardNumber = "1234567890123456";
        var transaction= Transaction.builder()
                .amount(100)
                .cardNumber(cardNumber)
                .userId(1)
                .date(LocalDateTime.now())
                .build();
        var paymentsResponse=PaymentsResponse.builder()
                .transactionList(new ArrayList<>())
                .success(true)
                .message("")
                .build();
        var request = MakePaymentRequest.builder()
                .cardNumber(cardNumber)
                .userId(1)
                .amount(100)
                .build();
        var user = User.builder()
                .id(1)
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("1234")
                .build();
        var card = CreditCard.builder()
                .id(1)
                .cardNumber(cardNumber)
                .user(user)
                .build();
        Mockito.when(cardService.findByCardNumber(cardNumber)).thenReturn(card);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        var result = paymentService.makePayment(request);
        assertEquals(result.isSuccess(),paymentsResponse.isSuccess());
    }

    @Test
    public void whenMakePayment_thenFailWithInvalidCardError() {
        String cardNumber = "";
        var transaction= Transaction.builder()
                .amount(100)
                .cardNumber(cardNumber)
                .userId(1)
                .date(LocalDateTime.now())
                .build();
        var paymentsResponse=PaymentsResponse.builder()
                .transactionList(new ArrayList<>())
                .success(false)
                .message("")
                .build();
        var request = MakePaymentRequest.builder()
                .cardNumber(cardNumber)
                .userId(1)
                .amount(100)
                .build();
        var user = User.builder()
                .id(1)
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("1234")
                .build();
        Mockito.when(cardService.findByCardNumber(cardNumber)).thenReturn(null);
        var result = paymentService.makePayment(request);
        assertEquals(result.isSuccess(),paymentsResponse.isSuccess());
    }


}
