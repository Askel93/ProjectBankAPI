package com.example.project.bank.api.service.impl;

import com.example.project.bank.api.entity.BankCardType;
import com.example.project.bank.api.exception.BankCardGenerateException;
import com.example.project.bank.api.service.BankCardService;
import com.example.project.bank.api.dao.BankCardDAO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.entity.BankCard;
import com.example.project.bank.api.entity.PaymentSystem;
import com.example.project.bank.api.service.util.BankCardCVVGenerator;
import com.example.project.bank.api.service.util.BankCardDateGenerator;
import com.example.project.bank.api.service.util.BankCardNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BankCardServiceImpl implements BankCardService {

    private BankCardDAO bankCardDAO;

    @Autowired
    public BankCardServiceImpl(BankCardDAO bankCardDAO) {
        this.bankCardDAO = bankCardDAO;
    }

    private String getUniqueCardNumber(String startString){
        String generatedCardNumber = BankCardNumberGenerator.generateCardNumber(startString);
        boolean generatedCardIsUnique = false;

        for (int i = 0; i < 100; i++) {
            if(bankCardDAO.findByNumber(generatedCardNumber) == null){
                generatedCardIsUnique = true;
            }
            else {
                generatedCardNumber = BankCardNumberGenerator.generateCardNumber(startString);
            }
        }
        if(!generatedCardIsUnique){
            throw new BankCardGenerateException(100);
        }

        return generatedCardNumber;

    }

    private String getStartSymbolsOfBankCard(PaymentSystem paymentSystem, BankCardType bankCardType){
        return paymentSystem.getPaymentSystemCode() + paymentSystem.getBankCode() + bankCardType.getBankCardTypeCode();
    }

    @Override
    public BankCard addBankCard(BankAccount bankAccount, PaymentSystem paymentSystem, BankCardType bankCardType) {

        String startString = getStartSymbolsOfBankCard(paymentSystem, bankCardType);

        String generatedCardNumber = getUniqueCardNumber(startString);
        String generatedCardCVV = BankCardCVVGenerator.generateCardCVV();
        LocalDate validThrough = BankCardDateGenerator.generateDateValidThrough();

        BankCard bankCard = new BankCard(generatedCardNumber,
                validThrough,
                generatedCardCVV,
                false,
                paymentSystem,
                bankAccount,
                bankCardType);

        return bankCardDAO.save(bankCard);
    }

    @Override
    public List<BankCard> findByBankAccountId(int bankAccountId) {
        return bankCardDAO.findByBankAccountId(bankAccountId);
    }


}
