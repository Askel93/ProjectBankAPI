package com.example.project.bank.api.dao;

import com.example.project.bank.api.entity.BankCard;

import java.util.List;

public interface BankCardDAO {
    List<BankCard> findByBankAccountId(int bankAccountId);
    BankCard save(BankCard bankCard);
    BankCard findByNumber(String cardNumber);
}
