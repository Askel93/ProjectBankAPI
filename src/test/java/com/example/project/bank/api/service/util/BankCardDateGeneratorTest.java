package com.example.project.bank.api.service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BankCardDateGeneratorTest {

    private LocalDate nowDate;
    private LocalDate generatedDate;
    @BeforeEach
    public void generateDate() {
        nowDate = LocalDate.now();
        generatedDate = BankCardDateGenerator.generateDateValidThrough();
    }

    @Test
    void generatedDateShouldBeGreaterThenNow() {
        assertTrue(generatedDate.isAfter(nowDate));
    }

    @Test
    void generatedDateShouldContainsFirstDayOfMonth() {
        assertEquals(generatedDate.getDayOfMonth() , 1);
    }

    @Test
    void generatedDateShouldBeOffsetByConstantNumberOfYears() {
        assertEquals(generatedDate.getYear() - nowDate.getYear() , BankCardDateGenerator.YEAR_OFFSET);
    }

}