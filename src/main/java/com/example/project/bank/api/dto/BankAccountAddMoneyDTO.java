package com.example.project.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountAddMoneyDTO {
    @NotNull(message = "property 'id' should be not null")
    private Integer id;
    @Digits(integer = 15, fraction = 2, message =  "in property 'sum' max decimal point is 2")
    @DecimalMin(value = "0.01", message = "for property 'sum' min value is 0.01")
    private double sum;
}
