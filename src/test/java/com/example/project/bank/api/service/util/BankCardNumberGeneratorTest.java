package com.example.project.bank.api.service.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertTrue;

class BankCardNumberGeneratorTest {

    @Test
    void generatedCardNumberShouldContainsNumbers() {
        String cardNumber = BankCardNumberGenerator.generateCardNumber("");

        assertThat(cardNumber, matchesPattern("^\\d{" + BankCardNumberGenerator.BANK_CARD_LENGTH + "}$"));
    }

    @Test
    void generatedCardNumberShouldStartWithParameter() {
        String startString = "427620";
        String cardNumber = BankCardNumberGenerator.generateCardNumber(startString);

        assertThat(cardNumber, matchesPattern("^"+ startString +"\\d{" + (BankCardNumberGenerator.BANK_CARD_LENGTH - startString.length()) + "}$"));
    }

    @Test
    void generatedCardNumberShouldReturnDifferentValue() {

        Set<String> cardNumbers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            cardNumbers.add(BankCardNumberGenerator.generateCardNumber(""));
        }

        assertTrue(cardNumbers.size() > 1);
    }




}