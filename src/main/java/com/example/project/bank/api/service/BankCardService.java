package com.example.project.bank.api.service;

import com.example.project.bank.api.dto.BankCardForAddDTO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.entity.BankCard;
import com.example.project.bank.api.entity.BankCardType;
import com.example.project.bank.api.entity.PaymentSystem;

import java.util.List;

public interface BankCardService {
    List<BankCard> findByBankAccountId(int bankAccountId);
    BankCard addBankCard(BankCardForAddDTO bankCardForAddDTO);
}
