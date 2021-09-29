package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankAccountDAO;
import com.example.project.bank.api.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BankAccountServiceImplTest {

    @Autowired
    BankAccountServiceImpl bankAccountService;
    @MockBean
    private BankAccountDAO bankAccountDAO;

    @Test
    void addMoney() {
        assertThrows(EntityNotFoundException.class, () -> bankAccountService.addMoney(1,10));
        verify(bankAccountDAO, Mockito.times(1)).findById(1);
    }

    @Test
    void findById() {
        assertThrows(EntityNotFoundException.class, () -> bankAccountService.findById(1));
        verify(bankAccountDAO, Mockito.times(1)).findById(1);
    }

    @Test
    void findBankCardsByBankAccountId() {
        assertThrows(EntityNotFoundException.class, () -> bankAccountService.findBankCardsByBankAccountId(1));
        verify(bankAccountDAO, Mockito.times(1)).findById(1);
    }
}