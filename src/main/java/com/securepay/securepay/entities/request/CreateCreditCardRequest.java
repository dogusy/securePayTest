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
public class CreateCreditCardRequest {
    @Schema(description = "User Id", example = "1", required = true)
    private Integer userId;
}
