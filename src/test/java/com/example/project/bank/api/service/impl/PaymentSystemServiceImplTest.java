package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.PaymentSystemDAO;
import com.example.project.bank.api.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PaymentSystemServiceImplTest {

    @Autowired
    PaymentSystemServiceImpl paymentSystemService;
    @MockBean
    private PaymentSystemDAO paymentSystemDAO;

    @Test
    void findById() {
        assertThrows(EntityNotFoundException.class, () -> paymentSystemService.findById(-1));
        verify(paymentSystemDAO, Mockito.times(1)).findById(any(Integer.class));
    }
}