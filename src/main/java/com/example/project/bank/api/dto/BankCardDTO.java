package com.example.project.bank.api.dto;

import com.example.project.bank.api.entity.BankCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankCardDTO {
    private Integer id;
    private String number;
    private String paymentSystemName;
    private String bankCardTypeName;

    public static BankCardDTO fromBankCard(BankCard bankCard){
        BankCardDTO bankCardDTO = new BankCardDTO();

        bankCardDTO.setId(bankCard.getId());
        bankCardDTO.setNumber(bankCard.getNumber());
        bankCardDTO.setBankCardTypeName(bankCard.getBankCardType().getName());
        bankCardDTO.setPaymentSystemName(bankCard.getPaymentSystem().getName());

        return bankCardDTO;
    }
}
