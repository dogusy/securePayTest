package com.securepay.securepay.utils.card;

import com.securepay.securepay.service.CardService;
import org.springframework.stereotype.Component;

import java.util.Random;

public class CardHelper {


    public static String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }


}
