package com.example.project.bank.api.dao.impl;

import com.example.project.bank.api.dao.BankCardTypeDAO;
import com.example.project.bank.api.entity.BankCardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class BankCardTypeDAOImpl implements BankCardTypeDAO {
    private final EntityManager entityManager;

    @Autowired
    public BankCardTypeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public BankCardType findById(int id) {
        return entityManager.find(BankCardType.class, id);
    }
}
