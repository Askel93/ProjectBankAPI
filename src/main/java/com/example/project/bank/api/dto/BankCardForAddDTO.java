package com.example.project.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCardForAddDTO {
    @NotNull(message = "property 'bankAccountDTO' should be not null'")
    private BankAccountIdDTO bankAccountIdDTO;
    @NotNull(message = "property 'paymentSystemIdDTO' should be not null'")
    private PaymentSystemIdDTO paymentSystemIdDTO;
    @NotNull(message = "property 'bankCardTypeDTO' should be not null'")
    private BankCardTypeIdDTO bankCardTypeIdDTO;
}