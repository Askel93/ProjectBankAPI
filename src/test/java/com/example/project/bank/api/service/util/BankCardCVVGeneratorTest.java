package com.example.project.bank.api.service.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertTrue;

class BankCardCVVGeneratorTest {

    @Test
    void generatedCardCVVShouldContainsNumbers() {
        String cardCVV = BankCardCVVGenerator.generateCardCVV();

        assertThat(cardCVV, matchesPattern("^\\d{" + BankCardCVVGenerator.CVV_LENGTH + "}$"));
    }

    @Test
    void generatedCardCVVShouldReturnDifferentValue() {

        Set<String> cardCVVs = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            cardCVVs.add(BankCardCVVGenerator.generateCardCVV());
        }

        assertTrue(cardCVVs.size() > 1);
    }

}