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

    private final BankCardService bankCardService;
    private final BankAccountService bankAccountService;

    @Autowired
    public BankCardRestController(BankCardService bankCardService, BankAccountService bankAccountService) {
        this.bankCardService = bankCardService;
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("")
    public BankCardDTO addBankCard(@Valid @RequestBody BankCardForAddDTO bankCardForAddDTO){

        BankCard bankCard = bankCardService.addBankCard(bankCardForAddDTO);

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
