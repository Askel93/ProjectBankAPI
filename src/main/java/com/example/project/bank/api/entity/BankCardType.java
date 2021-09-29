package com.example.project.bank.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_card_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCardType extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "bank_card_type_code")
    private String bankCardTypeCode = "";
}