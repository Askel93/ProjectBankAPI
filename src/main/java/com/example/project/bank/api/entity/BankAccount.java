package com.example.project.bank.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount extends BaseEntity{

    @Column(name = "number")
    private String number;

    @Column(name = "currency")
    private String currency;

    @Column(name = "balance")
    private double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankAccount")
    private List<BankCard> bankCardList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Person person;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + getId() + '\'' +
                "number='" + number + '\'' +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", bankCardList=" + bankCardList +
                '}';
    }
}
