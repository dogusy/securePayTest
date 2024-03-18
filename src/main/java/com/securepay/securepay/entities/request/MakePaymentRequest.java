package com.securepay.securepay.entities.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakePaymentRequest {

    @Schema(description = "Card number", example = "1234567890123456", required = true)
    private String cardNumber;
    @Schema(description = "User id", example = "1", required = true)
    private int userId;
    @Schema(description = "Amount", example = "100", required = true)
    private double amount;

}
