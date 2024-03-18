package com.securepay.securepay.entities.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentResponse {

        private String message;
        private boolean success;
}
