package com.example.project.bank.api.dto;

import com.example.project.bank.api.entity.PaymentSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSystemDTO {
    private String name;

    public static PaymentSystemDTO fromPaymentSystem(PaymentSystem paymentSystem){
        return new PaymentSystemDTO(paymentSystem.getName());
    }
}
