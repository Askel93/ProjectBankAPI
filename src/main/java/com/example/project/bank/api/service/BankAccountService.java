package com.example.project.bank.api.service;

import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.entity.BankCard;

import java.util.List;

public interface BankAccountService {
    BankAccount findById(int bankAccountId);
    List<BankCard> findBankCardsByBankAccountId(int bankAccountId);
    BankAccount addMoney(int bankAccountId, double money);
}
