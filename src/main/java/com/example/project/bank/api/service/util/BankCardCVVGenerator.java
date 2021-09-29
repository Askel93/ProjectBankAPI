package com.example.project.bank.api.service.util;

import java.util.Random;

public class BankCardCVVGenerator {

    public static final int CVV_LENGTH = 3;
    private static final Random random = new Random();

    private static int getRandomInt(){
        return random.nextInt(10);
    }

    public static String generateCardCVV(){

        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < CVV_LENGTH; i++) {
            stringBuilder.append(getRandomInt());
        }

        return stringBuilder.toString();
    }
}
