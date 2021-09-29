package com.example.project.bank.api.dto;

import com.example.project.bank.api.entity.BankCardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCardTypeDTO {
    private String name;

    public static BankCardTypeDTO fromPaymentSystem(BankCardType bankCardType){
        return new BankCardTypeDTO(bankCardType.getName());
    }

}
