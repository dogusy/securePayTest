package com.securepay.securepay.controller;

import com.securepay.securepay.entities.CreditCard;
import com.securepay.securepay.entities.request.*;
import com.securepay.securepay.entities.response.*;
import com.securepay.securepay.service.CustomerService;
import com.securepay.securepay.service.CardService;
import com.securepay.securepay.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CardService cardService;

    public CustomerController(CustomerService customerService, CardService cardService) {
        this.customerService = customerService;
        this.cardService = cardService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a customer", description = "Register a customer")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(customerService.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate a customer", description = "Authenticate a customer")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(customerService.authenticate(request));
    }

}
