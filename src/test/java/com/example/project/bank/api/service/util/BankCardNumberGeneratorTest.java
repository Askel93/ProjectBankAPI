package com.example.project.bank.api.service.util;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

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


}