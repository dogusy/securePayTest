package com.securepay.securepay.utils.card;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

   private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDateTime convertStringToLocalDateTime(String date){
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime startDate = localDate.atStartOfDay();
        return  startDate;
    }
}
