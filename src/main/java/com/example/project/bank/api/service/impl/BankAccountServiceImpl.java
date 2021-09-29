package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.dao.BankAccountDAO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.entity.BankCard;
import com.example.project.bank.api.exception.EntityNotFoundException;
import com.example.project.bank.api.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountDAO bankAccountDAO;

    @Autowired
    public BankAccountServiceImpl(BankAccountDAO bankAccountDAO) {
        this.bankAccountDAO = bankAccountDAO;
    }

    @Override
    public BankAccount addMoney(int bankAccountId, double money) {
        BankAccount bankAccount = findById(bankAccountId);
        bankAccount.setBalance(bankAccount.getBalance() + money);
        return bankAccountDAO.save(bankAccount);
    }

    @Override
    public BankAccount findById(int id) {
        BankAccount bankAccount = bankAccountDAO.findById(id);
        if(bankAccount == null){
            throw new EntityNotFoundException("BankAccount", id);
        }

        return bankAccount;
    }

    @Override
    public List<BankCard> findBankCardsByBankAccountId(int id){
        return findById(id).getBankCardList();
    }
}
