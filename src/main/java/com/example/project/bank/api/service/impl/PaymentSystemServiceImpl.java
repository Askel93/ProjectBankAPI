package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.entity.PaymentSystem;
import com.example.project.bank.api.exception.EntityNotFoundException;
import com.example.project.bank.api.service.PaymentSystemService;
import com.example.project.bank.api.dao.PaymentSystemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentSystemServiceImpl implements PaymentSystemService {
    private final PaymentSystemDAO paymentSystemDAO;

    @Autowired
    public PaymentSystemServiceImpl(PaymentSystemDAO paymentSystemDAO) {
        this.paymentSystemDAO = paymentSystemDAO;
    }

    @Override
    public PaymentSystem findById(int id) {
        PaymentSystem paymentSystem = paymentSystemDAO.findById(id);
        if(paymentSystem == null){
            throw new EntityNotFoundException("PaymentSystem", id);
        }

        return paymentSystem;
    }
}
