package com.example.project.bank.api.dao;

import com.example.project.bank.api.entity.BankAccount;

public interface BankAccountDAO {
    BankAccount findById(int id);
    BankAccount save(BankAccount bankAccount);
}
