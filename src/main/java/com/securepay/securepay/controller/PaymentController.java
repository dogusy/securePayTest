package com.securepay.securepay.controller;

import com.securepay.securepay.entities.request.CreateCreditCardRequest;
import com.securepay.securepay.entities.request.MakePaymentRequest;
import com.securepay.securepay.entities.request.QueryPaymentRequest;
import com.securepay.securepay.entities.response.CreateCreditCardResponse;
import com.securepay.securepay.entities.response.MakePaymentResponse;
import com.securepay.securepay.entities.response.PaymentsResponse;
import com.securepay.securepay.service.CardService;
import com.securepay.securepay.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final CardService cardService;
    private final PaymentService paymentService;

    public PaymentController(CardService cardService, PaymentService paymentService) {
        this.cardService = cardService;
        this.paymentService = paymentService;
    }

    @PostMapping("/createCreditCard")
    @Operation(summary = "Create a credit card", description = "Create a credit card")
    public ResponseEntity<CreateCreditCardResponse> createCreditCard(
            @RequestBody CreateCreditCardRequest request
    ){
        return ResponseEntity.ok(cardService.createCreditCard(request));
    }
    @PostMapping("/makePayment")
    @Operation(summary = "Make a payment", description = "Make a payment")
    public ResponseEntity<MakePaymentResponse> makePayment(
            @RequestBody MakePaymentRequest request
    ){
        return ResponseEntity.ok(paymentService.makePayment(request));
    }

    @GetMapping("/queryPaymentsByCustomerId/{customerId}")
    @Operation(summary = "Query payments by customer id", description = "Query payments by customer id")
    public ResponseEntity<PaymentsResponse> queryPaymentsByCustomerId(@PathVariable Integer customerId){
        return ResponseEntity.ok(paymentService.queryPaymentsByCustomerId(customerId));
    }

    @GetMapping("/queryPaymentsByCardNumber/{cardNumber}")
    @Operation(summary = "Query payments by card number", description = "Query payments by card number")
    public ResponseEntity<PaymentsResponse> queryPaymentsByCardNumber(@PathVariable String cardNumber){
        return ResponseEntity.ok(paymentService.queryPaymentsByCardNumber(cardNumber));
    }

    @GetMapping("/queryPaymentsByCustomerEmail/{customerEmail}")
    @Operation(summary = "Query payments by customer email", description = "Query payments by customer email")
    public ResponseEntity<PaymentsResponse> queryPaymentsByCustomerEmail(@PathVariable String customerEmail){
        return ResponseEntity.ok(paymentService.queryPaymentsByCustomerEmail(customerEmail));
    }
    @PostMapping("/queryPaymentsByStartDateAndEndDate")
    @Operation(summary = "Query payments by start date and end date", description = "Query payments by start date and end date")
    public ResponseEntity<PaymentsResponse> queryPaymentsByStartDateAndEndDate(
            @RequestBody QueryPaymentRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(paymentService.queryPaymentsByStartDateAndEndDate(request, page, size));
    }

}
