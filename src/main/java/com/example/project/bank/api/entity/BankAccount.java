package com.example.project.bank.api.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    public BankAccount(int id, String number, String currency, double balance) {
        this.number = number;
        this.currency = currency;
        this.balance = balance;
        this.setId(id);
    }

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
