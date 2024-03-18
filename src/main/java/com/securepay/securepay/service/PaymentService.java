package com.securepay.securepay.service;

import com.securepay.securepay.entities.Transaction;
import com.securepay.securepay.entities.User;
import com.securepay.securepay.entities.request.MakePaymentRequest;
import com.securepay.securepay.entities.request.QueryPaymentRequest;
import com.securepay.securepay.entities.response.MakePaymentResponse;
import com.securepay.securepay.entities.response.PaymentsResponse;
import com.securepay.securepay.repos.TransactionRepository;
import com.securepay.securepay.utils.card.DateHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final CardService cardService;

    public PaymentService(TransactionRepository transactionRepository, CustomerService customerService, CardService cardService) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
        this.cardService = cardService;
    }

    public PaymentsResponse queryPaymentsByCustomerId(Integer customerId) {
        try {
        List<Transaction> transactionList = this.transactionRepository.findByUserId(customerId);
        return PaymentsResponse.builder()
                .transactionList(transactionList)
                .success(true)
                .message("")
                .build();
        }catch (Exception e){
            return PaymentsResponse.builder()
                    .success(false)
                    .message("Invalid request")
                    .build();
        }
    }

    public PaymentsResponse queryPaymentsByCardNumber(String cardNumber) {
        try {
        List<Transaction> transactionList = this.transactionRepository.findByCardNumber(cardNumber);
        return PaymentsResponse.builder()
                .transactionList(transactionList)
                .success(true)
                .message("")
                .build();
        }catch (Exception e){
            return PaymentsResponse.builder()
                    .success(false)
                    .message("Invalid request")
                    .build();
        }
    }

    public PaymentsResponse queryPaymentsByCustomerEmail(String customerEmail) {
        try {
        var user = customerService.findUserByEmail(customerEmail) ;
        if (user == null){
            return null;
        }
        List<Transaction> transactionList = this.transactionRepository.findByUserId(user.getId());
        return PaymentsResponse.builder()
                .transactionList(transactionList)
                .success(true)
                .message("")
                .build();
        }catch (Exception e){
            return PaymentsResponse.builder()
                    .success(false)
                    .message("Invalid request")
                    .build();
        }
    }

    public PaymentsResponse queryPaymentsByStartDateAndEndDate(QueryPaymentRequest request, int page, int size) {
        try {
        if (request.getStartDate() == null || request.getEndDate() == null || request.getCardNumber()==null){
            return PaymentsResponse.builder()
                    .success(false)
                    .message("Invalid request")
                    .build();
        }

        LocalDateTime startDate = DateHelper.convertStringToLocalDateTime(request.getStartDate());
        LocalDateTime endDate = DateHelper.convertStringToLocalDateTime(request.getEndDate()).plusDays(1);
        Pageable paging = PageRequest.of(page, size);
        List<Transaction> transactionList = this.transactionRepository.findByCardNumberAndDateBetween(request.getCardNumber(), startDate, endDate,paging).toList();

        return PaymentsResponse.builder()
                .success(true)
                .message("")
                .transactionList(transactionList)
                .build();
        }catch (Exception e){
            return PaymentsResponse.builder()
                    .success(false)
                    .message("Invalid request")
                    .build();
        }
    }

    public MakePaymentResponse makePayment(MakePaymentRequest request) {
        try {
        var creditCard = cardService.findByCardNumber(request.getCardNumber());
        String message ="";
        boolean success=true;
        if (creditCard == null){
            message = "Invalid card";
            success = false;
        }
        else if (creditCard.getUser().getId() != request.getUserId()){
            message = "Invalid user";
            success = false;
        }
        else if (request.getAmount()<=0){
            message = "Invalid amount";
            success = false;
        }
        if (success){
            var trasaction = Transaction.builder()
                    .cardNumber(creditCard.getCardNumber())
                    .amount(request.getAmount())
                    .userId(request.getUserId())
                    .date(LocalDateTime.now())
                    .build();
            transactionRepository.save(trasaction);
        }
        message = success ? "Payment successful" : message;
        return MakePaymentResponse.builder()
                .message(message)
                .success(success)
                .build();
        }catch (Exception e){
            return MakePaymentResponse.builder()
                    .success(false)
                    .message("Payment failed")
                    .build();
        }
    }

}
