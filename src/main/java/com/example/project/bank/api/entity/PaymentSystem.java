package com.example.project.bank.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "payment_systems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSystem extends BaseEntity{

    @Column(name = "name")
    private String name;
    @Column(name = "payment_system_code")
    private String paymentSystemCode = "";
    @Column(name = "bank_code")
    private String bankCode = "";

}
