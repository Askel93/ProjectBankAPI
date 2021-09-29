package com.example.project.bank.api.controller;

import com.example.project.bank.api.dto.BankAccountIdDTO;
import com.example.project.bank.api.dto.BankCardDTO;
import com.example.project.bank.api.entity.BankCard;
import com.example.project.bank.api.entity.BankCardType;
import com.example.project.bank.api.entity.PaymentSystem;
import com.example.project.bank.api.service.BankCardService;
import com.example.project.bank.api.dto.BankCardForAddDTO;
import com.example.project.bank.api.entity.BankAccount;
import com.example.project.bank.api.service.BankAccountService;
import com.example.project.bank.api.service.BankCardTypeService;
import com.example.project.bank.api.service.PaymentSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bank-cards")
public class BankCardRestController {

    private final BankAccountService bankAccountService;
    private final BankCardService bankCardService;
    private final PaymentSystemService paymentSystemService;
    private final BankCardTypeService bankCardTypeService;

    @Autowired
    public BankCardRestController(BankAccountService bankAccountService,
                                  BankCardService bankCardService,
                                  PaymentSystemService paymentSystemService,
                                  BankCardTypeService bankCardTypeService) {
        this.bankAccountService = bankAccountService;
        this.bankCardService = bankCardService;
        this.paymentSystemService = paymentSystemService;
        this.bankCardTypeService = bankCardTypeService;
    }

    @PostMapping("")
    public BankCardDTO addBankCard(@Valid @RequestBody BankCardForAddDTO bankCardForAddDTO){

        BankAccount bankAccount = bankAccountService.findById(bankCardForAddDTO.getBankAccountIdDTO().getId());
        PaymentSystem paymentSystem = paymentSystemService.findById(bankCardForAddDTO.getPaymentSystemIdDTO().getId());
        BankCardType bankCardType = bankCardTypeService.findById(bankCardForAddDTO.getBankCardTypeIdDTO().getId());

        BankCard bankCard = bankCardService.addBankCard(bankAccount, paymentSystem, bankCardType);

        return BankCardDTO.fromBankCard(bankCard);

    }

    @GetMapping("")
    public List<BankCardDTO> getByBankAccountId(@Valid @RequestBody BankAccountIdDTO bankAccountIdDTO) {

        List<BankCard> bankCards = bankAccountService.findBankCardsByBankAccountId(bankAccountIdDTO.getId());

        return bankCards.stream()
                .map(BankCardDTO::fromBankCard)
                .collect(Collectors.toList());
    }

}
