package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankCardTypeDAO;
import com.example.project.bank.api.entity.BankCardType;
import com.example.project.bank.api.exception.EntityNotFoundException;
import com.example.project.bank.api.service.BankCardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankCardTypeServiceImpl implements BankCardTypeService {
    private final BankCardTypeDAO bankCardTypeDAO;

    @Autowired
    public BankCardTypeServiceImpl(BankCardTypeDAO bankCardTypeDAO) {
        this.bankCardTypeDAO = bankCardTypeDAO;
    }

    @Override
    public BankCardType findById(int id) {
        BankCardType bankCardType = bankCardTypeDAO.findById(id);
        if(bankCardType == null){
            throw new EntityNotFoundException("BankCardType", id);
        }

        return bankCardType;
    }
}
