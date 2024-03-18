package com.securepay.securepay.entities.response;

import com.securepay.securepay.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsResponse {
    private List<Transaction> transactionList;
    private String message;
    private boolean success;
}
