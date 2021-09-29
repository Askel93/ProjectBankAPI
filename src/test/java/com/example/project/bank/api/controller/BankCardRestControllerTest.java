package com.example.project.bank.api.controller;

import com.example.project.bank.api.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.example.project.bank.api.converter.JsonToObjectConverter.convertJSONStringToObject;
import static com.example.project.bank.api.converter.ObjectToJsonConverter.toJson;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BankCardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankCardRestController restController;

    private final String urlGetByAccountId = "/api/bank-cards";
    private final String urlAddBankCard = "/api/bank-cards";
    private final String contentTypeJson = "application/json";

    @Test
    public void controllerInitialized() {
        assertNotNull(restController);
    }

    @Test
    void getByBankAccountIdIsOk() throws Exception {

        BankAccountIdDTO dto = new BankAccountIdDTO(1);

        MvcResult result = this.mockMvc.perform(get(urlGetByAccountId).
                        content(toJson(dto)).
                        contentType(contentTypeJson).
                        accept(contentTypeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        List<BankCardDTO> responseDTO = (List<BankCardDTO>) convertJSONStringToObject(
                result.getResponse().getContentAsString(), List.class);

        assertNotNull(responseDTO);
    }

    @Test
    public void getByBankAccountIdIsNotFound() throws Exception {
        BankAccountIdDTO dto = new BankAccountIdDTO(-9);

        MvcResult result = this.mockMvc.perform(get(urlGetByAccountId).
                        content(toJson(dto)).
                        contentType(contentTypeJson).
                        accept(contentTypeJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        ErrorDTO responseDTO = (ErrorDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(responseDTO.getErrorCode(),1);
        assertNotNull(responseDTO.getErrorMessage());
    }

    @Test
    public void getByBankAccountIdIsBadRequest() throws Exception {

        MvcResult result = this.mockMvc.perform(get(urlGetByAccountId).
                        content("{sadoh34ks}").
                        contentType(contentTypeJson).
                        accept(contentTypeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        ErrorDTO responseDTO = (ErrorDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(responseDTO.getErrorCode(),5);
        assertNotNull(responseDTO.getErrorMessage());
    }

    @Test
    void addBankCardIsOk() throws Exception{

        BankCardForAddDTO dto = new BankCardForAddDTO(new BankAccountIdDTO(1), new PaymentSystemIdDTO(1), new BankCardTypeIdDTO(1));

        MvcResult result = this.mockMvc.perform(post(urlAddBankCard)
                        .content(toJson(dto))
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        BankCardDTO responseDTO = (BankCardDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), BankCardDTO.class);

        assertNotNull(responseDTO.getNumber());
        assertNotNull(responseDTO.getBankCardTypeName());
        assertNotNull(responseDTO.getId());
        assertNotNull(responseDTO.getPaymentSystemName());
    }

    @Test
    void addMoneyIsNotFound() throws Exception{

        BankCardForAddDTO dto = new BankCardForAddDTO(new BankAccountIdDTO(-1), new PaymentSystemIdDTO(1), new BankCardTypeIdDTO(1));

        MvcResult result = this.mockMvc.perform(post(urlAddBankCard)
                        .content(toJson(dto))
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        ErrorDTO responseDTO = (ErrorDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(responseDTO.getErrorCode(),1);
        assertNotNull(responseDTO.getErrorMessage());
    }

    @Test
    void addMoneyIsBadRequest() throws Exception{

        MvcResult result = this.mockMvc.perform(post(urlAddBankCard)
                        .content("{sadoh34ks}")
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        ErrorDTO responseDTO = (ErrorDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(responseDTO.getErrorCode(),5);
        assertNotNull(responseDTO.getErrorMessage());
    }

    @Test
    void addMoneyIsBadRequestNegativeSum() throws Exception{

        BankCardForAddDTO dto = new BankCardForAddDTO(new BankAccountIdDTO(1), null, new BankCardTypeIdDTO(1));

        MvcResult result = this.mockMvc.perform(post(urlAddBankCard)
                        .content(toJson(dto))
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        ErrorDTO responseDTO = (ErrorDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(responseDTO.getErrorCode(),6);
        assertNotNull(responseDTO.getErrorMessage());
    }

    @Test
    void addMoneyIsBadRequestTooManyFraction() throws Exception{

        BankCardForAddDTO dto = new BankCardForAddDTO(null, new PaymentSystemIdDTO(1), new BankCardTypeIdDTO(1));

        MvcResult result = this.mockMvc.perform(post(urlAddBankCard)
                        .content(toJson(dto))
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        ErrorDTO responseDTO = (ErrorDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(responseDTO.getErrorCode(),6);
        assertNotNull(responseDTO.getErrorMessage());
    }

}