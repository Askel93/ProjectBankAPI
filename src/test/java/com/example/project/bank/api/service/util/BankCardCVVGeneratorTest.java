package com.example.project.bank.api.service.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

class BankCardCVVGeneratorTest {

    @Test
    void generatedCardCVVShouldContainsNumbers() {
        String cardNumber = BankCardCVVGenerator.generateCardCVV();

        assertThat(cardNumber, matchesPattern("^\\d{" + BankCardCVVGenerator.CVV_LENGTH + "}$"));
    }

}