package com.example.project.bank.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bank_cards")
@Getter
@Setter
@NoArgsConstructor
public class BankCard extends BaseEntity{

    @Column(name = "number")
    private String number;

    @Column(name = "valid_through")
    private LocalDate validThrough;

    @Column(name = "CVV")
    private String CVV;

    @Column(name = "issued")
    private boolean issued;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_system_id")
    private PaymentSystem paymentSystem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_card_type_id")
    private BankCardType bankCardType;

    public BankCard(String number,
                    LocalDate validThrough,
                    String CVV,
                    boolean issued,
                    PaymentSystem paymentSystem,
                    BankAccount bankAccount,
                    BankCardType bankCardType) {
        this.number = number;
        this.validThrough = validThrough;
        this.CVV = CVV;
        this.issued = issued;
        this.paymentSystem = paymentSystem;
        this.bankAccount = bankAccount;
        this.bankCardType = bankCardType;
    }

}
