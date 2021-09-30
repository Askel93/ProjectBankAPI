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

    private final BankAccountIdDTO validBankAccountIdDTO = new BankAccountIdDTO(1);
    private final PaymentSystemIdDTO validPaymentSystemDTO = new PaymentSystemIdDTO(1);
    private final BankCardTypeIdDTO validBankCardTypeIdDTO = new BankCardTypeIdDTO(1);

    private final BankAccountIdDTO notFoundBankAccountIdDTO = new BankAccountIdDTO(-1);
    private final PaymentSystemIdDTO notFoundPaymentSystemDTO = new PaymentSystemIdDTO(-1);
    private final BankCardTypeIdDTO notFoundBankCardTypeIdDTO = new BankCardTypeIdDTO(-1);

    @Test
    public void controllerInitialized() {
        assertNotNull(restController);
    }

    @Test
    void getByBankAccountIdIsOk() throws Exception {

        MvcResult result = this.mockMvc.perform(get(urlGetByAccountId).
                        content(toJson(validBankAccountIdDTO)).
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

        MvcResult result = this.mockMvc.perform(get(urlGetByAccountId).
                        content(toJson(notFoundBankAccountIdDTO)).
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

        BankCardForAddDTO dto =
                new BankCardForAddDTO(validBankAccountIdDTO, validPaymentSystemDTO, validBankCardTypeIdDTO);

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
    void addBankCardIsNotFoundBankAccount() throws Exception{

        BankCardForAddDTO dto =
                new BankCardForAddDTO(notFoundBankAccountIdDTO, validPaymentSystemDTO, validBankCardTypeIdDTO);

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
    void addBankCardIsNotFoundPaymentSystem() throws Exception{

        BankCardForAddDTO dto =
                new BankCardForAddDTO(validBankAccountIdDTO, notFoundPaymentSystemDTO, validBankCardTypeIdDTO);

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
    void addBankCardIsNotFoundBankCardType() throws Exception{

        BankCardForAddDTO dto =
                new BankCardForAddDTO(validBankAccountIdDTO, validPaymentSystemDTO, notFoundBankCardTypeIdDTO);

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
    void addBankCardIsBadRequest() throws Exception{

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
    void addBankCardIsBadRequestInvalidPaymentSystem() throws Exception{

        BankCardForAddDTO dto =
                new BankCardForAddDTO(validBankAccountIdDTO, null, validBankCardTypeIdDTO);

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
    void addBankCardIsBadRequestInvalidBankAccount() throws Exception{

        BankCardForAddDTO dto =
                new BankCardForAddDTO(null, validPaymentSystemDTO, validBankCardTypeIdDTO);

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
    void addBankCardIsBadRequestInvalidBankCardType() throws Exception{

        BankCardForAddDTO dto =
                new BankCardForAddDTO(validBankAccountIdDTO, validPaymentSystemDTO, null);

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