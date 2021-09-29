package com.example.project.bank.api.controller;

import com.example.project.bank.api.dto.BankAccountAddMoneyDTO;
import com.example.project.bank.api.dto.BankAccountBalanceDTO;
import com.example.project.bank.api.dto.BankAccountIdDTO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/balance")
    public BankAccountBalanceDTO getBalance(@Valid @RequestBody BankAccountIdDTO bankAccountIdDTO){

        return BankAccountBalanceDTO.fromBankAccount(bankAccountService.findById(bankAccountIdDTO.getId()));

    }

    @PatchMapping("/balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BankAccountBalanceDTO addMoney(@Valid @RequestBody BankAccountAddMoneyDTO bankAccountAddMoneyDTO){

        BankAccount bankAccount = bankAccountService.addMoney(bankAccountAddMoneyDTO.getId(), bankAccountAddMoneyDTO.getSum());
        return BankAccountBalanceDTO.fromBankAccount(bankAccount);

    }

}
