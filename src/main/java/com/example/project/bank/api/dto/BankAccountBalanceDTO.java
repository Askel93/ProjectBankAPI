package com.example.project.bank.api.dto;

import com.example.project.bank.api.entity.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountBalanceDTO {
    private String currency;
    private Double balance;

    public static BankAccountBalanceDTO fromBankAccount(BankAccount bankAccount){
        return new BankAccountBalanceDTO(bankAccount.getCurrency(), bankAccount.getBalance());
    }
}