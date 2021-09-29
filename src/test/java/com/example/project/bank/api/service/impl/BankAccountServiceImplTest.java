package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankAccountDAO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BankAccountServiceImplTest {

    @Autowired
    BankAccountServiceImpl bankAccountService;
    @MockBean
    private BankAccountDAO bankAccountDAO;

    @Test
    void addMoney() {

//        when(bankAccountService.addMoney(1,10)).thenReturn(new BankAccount(1,"11111222223333344444","EUR", 1000));
//        when(bankAccountService.addMoney(99999,10)).thenThrow(new EntityNotFoundException("test",4));
        when(bankAccountDAO.findById(1)).thenReturn(new BankAccount(1,"11111222223333344444","EUR", 1000));
        when(bankAccountDAO.findById(-1)).thenThrow(new EntityNotFoundException("test",-1));

        when(bankAccountDAO.findById(1)).thenReturn(new BankAccount(1,"11111222223333344444","EUR", 1000));

//        assertThrows(EntityNotFoundException.class, () -> bankAccountService.addMoney(1,10));
//        verify(bankAccountDAO, Mockito.times(1)).findById(1);

//        when(bankAccountService.addMoney(value,10))
//                .thenThrow(EntityNotFoundException.class)
//                .thenReturn(new BankAccount());

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