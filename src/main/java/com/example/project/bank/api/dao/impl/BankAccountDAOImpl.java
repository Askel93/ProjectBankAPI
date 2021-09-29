package com.example.project.bank.api.dao.impl;

import com.example.project.bank.api.dao.BankAccountDAO;
import com.example.project.bank.api.entity.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class BankAccountDAOImpl implements BankAccountDAO {
    private final EntityManager entityManager;

    @Autowired
    public BankAccountDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public BankAccount save(BankAccount bankAccount) {
        return entityManager.merge(bankAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccount findById(int id) {
        return entityManager.find(BankAccount.class, id);
    }
}


