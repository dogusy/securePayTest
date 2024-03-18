package com.securepay.securepay.entities;

import com.securepay.securepay.utils.converters.StringCryptoConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creditCards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "card_number", nullable = false)
    @Convert(converter = StringCryptoConverter.class)
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
