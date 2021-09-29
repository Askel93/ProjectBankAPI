package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankCardTypeDAO;
import com.example.project.bank.api.entity.BankCardType;
import com.example.project.bank.api.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
class BankCardTypeServiceImplTest {

    @Autowired
    BankCardTypeServiceImpl bankCardTypeService;
    @MockBean
    BankCardTypeDAO bankCardTypeDAO;

    private final int id = 1;
    private final BankCardType testBankCardType = new BankCardType("test", "100");

    @Test
    void findByIdSuccess() {

        given(bankCardTypeDAO.findById(id)).willReturn(testBankCardType);

        BankCardType bankCardType = bankCardTypeService.findById(id);

        assertEquals(bankCardType,testBankCardType);
        then(bankCardTypeDAO).should(times(1)).findById(id);

    }

    @Test
    void findByIdEntityNotFoundException() {

        given(bankCardTypeDAO.findById(id)).willReturn(null);

        assertThrows(EntityNotFoundException.class, () -> bankCardTypeService.findById(id));

        then(bankCardTypeDAO).should(times(1)).findById(id);

    }
}