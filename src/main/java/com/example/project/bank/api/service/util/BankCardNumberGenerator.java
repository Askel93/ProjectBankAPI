package com.example.project.bank.api.service.util;

import java.util.Random;

public class BankCardNumberGenerator {

    public static final int BANK_CARD_LENGTH = 16;
    private static final Random random = new Random();

    private static int getRandomInt(){
        return random.nextInt(10);
    }

    public static String generateCardNumber(String startWith){

        StringBuilder stringBuilder = new StringBuilder(startWith == null ? "" : startWith);
        for (int i = startWith.length(); i < BANK_CARD_LENGTH; i++) {
            stringBuilder.append(getRandomInt());
        }

        return stringBuilder.toString();
    }

}
