package com.securepay.securepay.entities.response;

import com.securepay.securepay.entities.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardResponse{
    private CreditCard creditCard;
    private String message;
    private boolean success;

}
