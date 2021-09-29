package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankCardDAO;
import com.example.project.bank.api.dto.BankAccountIdDTO;
import com.example.project.bank.api.dto.BankCardForAddDTO;
import com.example.project.bank.api.dto.BankCardTypeIdDTO;
import com.example.project.bank.api.dto.PaymentSystemIdDTO;
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

    private static BankCardForAddDTO bankCardForAddDTO = new BankCardForAddDTO(new BankAccountIdDTO(1), new PaymentSystemIdDTO(1), new BankCardTypeIdDTO(1));

    @Test
    void addBankCard() {
        BankCard bankCard = bankCardServiceImpl.addBankCard(bankCardForAddDTO);

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