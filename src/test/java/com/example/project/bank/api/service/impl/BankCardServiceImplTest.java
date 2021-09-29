package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankCardDAO;
import com.example.project.bank.api.dto.BankAccountIdDTO;
import com.example.project.bank.api.dto.BankCardForAddDTO;
import com.example.project.bank.api.dto.BankCardTypeIdDTO;
import com.example.project.bank.api.dto.PaymentSystemIdDTO;
import com.example.project.bank.api.entity.BankCard;
import com.example.project.bank.api.exception.BankCardGenerateException;
import com.example.project.bank.api.exception.EntityNotFoundException;
import com.example.project.bank.api.service.BankAccountService;
import com.example.project.bank.api.service.BankCardTypeService;
import com.example.project.bank.api.service.PaymentSystemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BankCardServiceImplTest {

    @Autowired
    BankCardServiceImpl bankCardServiceImpl;
    @SpyBean
    private BankCardDAO bankCardDAO;
    @SpyBean
    private BankAccountService bankAccountService;
    @SpyBean
    private PaymentSystemService paymentSystemService;
    @SpyBean
    private BankCardTypeService bankCardTypeService;


    private static BankCardForAddDTO bankCardForAddDTO
            = new BankCardForAddDTO(new BankAccountIdDTO(1), new PaymentSystemIdDTO(1), new BankCardTypeIdDTO(1));

    @Test
    void addBankCardSuccess() {

        BankCard bankCard = bankCardServiceImpl.addBankCard(bankCardForAddDTO);

        assertEquals(bankCard.getBankCardType().getId(), bankCardForAddDTO.getBankCardTypeIdDTO().getId());
        assertEquals(bankCard.getBankAccount().getId(), bankCardForAddDTO.getBankAccountIdDTO().getId());
        assertEquals(bankCard.getPaymentSystem().getId(), bankCardForAddDTO.getPaymentSystemIdDTO().getId());

        assertNotNull(bankCard.getNumber());
        assertNotNull(bankCard.getCVV());
        assertNotNull(bankCard.getValidThrough());
        assertFalse(bankCard.isIssued());

        verify(bankCardDAO, Mockito.times(1)).save(any(BankCard.class));
        verify(bankCardDAO, Mockito.atLeast(1)).findByNumber(any(String.class));
        verify(bankCardDAO, Mockito.atMost(100)).findByNumber(any(String.class));

    }

    @Test
    void addBankCardFailDuringGeneratingCardNumber() {

        BankCard bankCard = new BankCard();

        when(bankCardDAO.findByNumber(any())).thenReturn(bankCard);
        assertThrows(BankCardGenerateException.class, () -> bankCardServiceImpl.addBankCard(bankCardForAddDTO));
        verify(bankCardDAO, Mockito.times(100)).findByNumber(any());
        verify(bankCardDAO, Mockito.never()).save(any());

    }

    @Test
    void addBankCardFailDuringGetPaymentSystem() {

        when(paymentSystemService.findById(1)).thenThrow(new EntityNotFoundException("test",1));
        assertThrows(EntityNotFoundException.class, () -> bankCardServiceImpl.addBankCard(bankCardForAddDTO));

        verify(bankCardDAO, Mockito.never()).findByNumber(any());
        verify(bankCardDAO, Mockito.never()).save(any());

    }

    @Test
    void addBankCardFailDuringGetBankAccount() {

        when(bankAccountService.findById(1)).thenThrow(new EntityNotFoundException("test",1));
        assertThrows(EntityNotFoundException.class, () -> bankCardServiceImpl.addBankCard(bankCardForAddDTO));

        verify(bankCardDAO, Mockito.never()).findByNumber(any());
        verify(bankCardDAO, Mockito.never()).save(any());

    }

    @Test
    void addBankCardFailDuringGetBankCardType() {

        when(bankCardTypeService.findById(1)).thenThrow(new EntityNotFoundException("test",1));
        assertThrows(EntityNotFoundException.class, () -> bankCardServiceImpl.addBankCard(bankCardForAddDTO));

        verify(bankCardDAO, Mockito.never()).findByNumber(any());
        verify(bankCardDAO, Mockito.never()).save(any());

    }

    @Test
    void findByBankAccountId(){
        List<BankCard> bankCard = bankCardServiceImpl.findByBankAccountId(1);

        verify(bankCardDAO, Mockito.times(1)).findByBankAccountId(1);
    }

}