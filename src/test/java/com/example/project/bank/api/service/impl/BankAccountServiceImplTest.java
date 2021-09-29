package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankAccountDAO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.entity.BankCard;
import com.example.project.bank.api.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class BankAccountServiceImplTest {

    @Autowired
    BankAccountServiceImpl bankAccountService;
    @SpyBean
    private BankAccountDAO bankAccountDAO;

    private final int bankAccountId = 1;

    @Test
    void addMoneySuccess() {

        double balanceBefore = bankAccountService.findById(bankAccountId).getBalance();
        double sum = 11.11;

        double balanceAfter = bankAccountService.addMoney(bankAccountId, sum).getBalance();

        assertEquals((int)(balanceAfter * 100) - (int)(balanceBefore * 100), sum * 100);
        then(bankAccountDAO).should(times(2)).findById(bankAccountId);
        then(bankAccountDAO).should(times(1)).save(any());

    }

    @Test
    void addMoneyEntityNotFoundException() {

        given(bankAccountDAO.findById(bankAccountId)).willReturn(null);

        double sum = 11.11;

        assertThrows(EntityNotFoundException.class, () -> bankAccountService.addMoney(bankAccountId, sum));

        then(bankAccountDAO).should(times(1)).findById(bankAccountId);
        then(bankAccountDAO).should(never()).save(any());

    }

    @Test
    void findByIdSuccess() {

        BankAccount bankAccountTest = new BankAccount(bankAccountId,"11111222223333344444","EUR", 1000);

        given(bankAccountDAO.findById(bankAccountId)).willReturn(bankAccountTest);

        BankAccount bankAccount = bankAccountService.findById(bankAccountId);

        assertNotNull(bankAccount);
        assertEquals(bankAccount,bankAccountTest);

        then(bankAccountDAO).should(times(1)).findById(bankAccountId);

    }

    @Test
    void findByIdEntityNotFoundException() {

        given(bankAccountDAO.findById(bankAccountId)).willReturn(null);

        assertThrows(EntityNotFoundException.class, () -> bankAccountService.findBankCardsByBankAccountId(bankAccountId));

        then(bankAccountDAO).should(times(1)).findById(bankAccountId);
    }

    @Test
    void findBankCardsByBankAccountIdSuccess(){

        List<BankCard> bankCardList = bankAccountService.findBankCardsByBankAccountId(bankAccountId);
        
        assertNotNull(bankCardList);

    }

    @Test
    void findBankCardsByBankAccountIdEntityNotFound(){

        given(bankAccountDAO.findById(bankAccountId)).willReturn(null);

        assertThrows(EntityNotFoundException.class, () -> bankAccountService.findBankCardsByBankAccountId(bankAccountId));

        then(bankAccountDAO).should(times(1)).findById(bankAccountId);

    }


}