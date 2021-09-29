package com.example.project.bank.api.service.util;

import java.time.LocalDate;

public class BankCardDateGenerator {

    public static final int YEAR_OFFSET = 4;

    public static LocalDate generateDateValidThrough(){
        LocalDate localDate = LocalDate.now().withDayOfMonth(1).plusYears(YEAR_OFFSET);

        return localDate;
    }

}
