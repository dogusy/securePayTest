package com.securepay.securepay.utils.converters;

import jakarta.persistence.Converter;



@Converter
public class StringCryptoConverter extends AbstractCryptoConverter<String> {

    public StringCryptoConverter() {
        this(new CipherInitializer());
    }

    public StringCryptoConverter(CipherInitializer cipherInitializer) {
        super(cipherInitializer);
    }

    @Override
    boolean isNotNullOrEmpty(String attribute) {
        return attribute!= null && !attribute.isEmpty();
    }

    @Override
    String stringToEntityAttribute(String dbData) {
        return dbData;
    }

    @Override
    String entityAttributeToString(String attribute) {
        return attribute;
    }
}