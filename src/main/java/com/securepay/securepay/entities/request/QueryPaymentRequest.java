package com.securepay.securepay.entities.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryPaymentRequest {

    @DateTimeFormat(pattern="dd-MM-yyyy")
    @Schema(description = "Start date", example = "01-01-2024", required = true)
    private String startDate;

    @DateTimeFormat(pattern="dd-MM-yyyy")
    @Schema(description = "End date", example = "01-01-2024", required = true)
    private String endDate;

    @Schema(description = "Card number", example = "1234567890123456", required = true)
    private String cardNumber;

    @Schema(description = "Page number", example = "0")
    private int page;

    @Schema(description = "Size of the page", example = "10")
    private int size;
}
