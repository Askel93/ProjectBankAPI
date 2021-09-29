package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankCardDAO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.entity.BankCard;
import com.example.project.bank.api.entity.BankCardType;
import com.example.project.bank.api.entity.PaymentSystem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BankCardServiceImplTest {

    @Autowired
    BankCardServiceImpl bankCardServiceImpl;
    @MockBean
    private BankCardDAO bankCardDAO;

    private static BankAccount bankAccount = new BankAccount();
    private static PaymentSystem paymentSystem = new PaymentSystem();
    private static BankCardType bankCardType = new BankCardType();

    @Test
    void addBankCard() {
        BankCard bankCard = bankCardServiceImpl.addBankCard(bankAccount, paymentSystem, bankCardType);

        verify(bankCardDAO, Mockito.times(1)).save(any(BankCard.class));
        verify(bankCardDAO, Mockito.atLeast(1)).findByNumber(any(String.class));
        verify(bankCardDAO, Mockito.atMost(100)).findByNumber(any(String.class));
    }

    @Test
    void findByBankAccountId(){
        List<BankCard> bankCard = bankCardServiceImpl.findByBankAccountId(1);

        verify(bankCardDAO, Mockito.times(1)).findByBankAccountId(1);
    }

}