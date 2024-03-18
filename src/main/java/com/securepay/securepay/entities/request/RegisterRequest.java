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
public class RegisterRequest {

    @Schema(description = "First name of the user", example = "John", required = true)
    private String firstname;
    @Schema(description = "Last name of the user", example = "Doe", required = true)
    private String lastname;
    @Schema(description = "Email of the user", example = "john@gmail.com", required = true)
    private String email;
    @Schema(description = "Password of the user", example = "password", required = true)
    private String password;

}
