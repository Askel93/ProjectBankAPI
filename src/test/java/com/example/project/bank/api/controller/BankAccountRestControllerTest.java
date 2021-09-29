package com.example.project.bank.api.controller;

import com.example.project.bank.api.dto.BankAccountAddMoneyDTO;
import com.example.project.bank.api.dto.BankAccountBalanceDTO;
import com.example.project.bank.api.dto.BankAccountIdDTO;
import com.example.project.bank.api.dto.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.project.bank.api.converter.JsonToObjectConverter.convertJSONStringToObject;
import static com.example.project.bank.api.converter.ObjectToJsonConverter.toJson;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BankAccountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankAccountRestController restController;

    private final String urlGetBalance = "/api/bank-accounts/balance";
    private final String urlAddMoney = "/api/bank-accounts/balance";
    private final String contentTypeJson = "application/json";

    @Test
    public void controllerInitialized() {
        assertNotNull(restController);
    }

    @Test
    public void getBalanceIsOk() throws Exception {

        BankAccountIdDTO dto = new BankAccountIdDTO(1);

        MvcResult result = this.mockMvc.perform(get(urlGetBalance).
                        content(toJson(dto)).
                        contentType(contentTypeJson).
                        accept(contentTypeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        BankAccountBalanceDTO responseDTO = (BankAccountBalanceDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), BankAccountBalanceDTO.class);

        assertNotNull(responseDTO.getCurrency());
        assertNotNull(responseDTO.getBalance());
    }

    @Test
    public void getBalanceIsNotFound() throws Exception {

        BankAccountIdDTO dto = new BankAccountIdDTO(-9);

        MvcResult result = this.mockMvc.perform(get(urlGetBalance)
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
    public void getBalanceIsBadRequest() throws Exception {

        MvcResult result = this.mockMvc.perform(get(urlGetBalance).
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
    void addMoneyIsOk() throws Exception{

        BankAccountAddMoneyDTO dto = new BankAccountAddMoneyDTO(1,10.11);

        MvcResult result = this.mockMvc.perform(patch(urlAddMoney)
                        .content(toJson(dto))
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        BankAccountBalanceDTO responseDTO = (BankAccountBalanceDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), BankAccountBalanceDTO.class);

        assertNotNull(responseDTO.getCurrency());
        assertNotNull(responseDTO.getBalance());
    }

    @Test
    void addMoneyIsOkCheckChanges() throws Exception{

        int accountId = 1;
        double sum = 10.11;

        BankAccountIdDTO bankAccountIdDTO = new BankAccountIdDTO(accountId);
        MvcResult result = this.mockMvc.perform(get(urlGetBalance)
                        .content(toJson(bankAccountIdDTO))
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        BankAccountBalanceDTO responseDTO1 = (BankAccountBalanceDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), BankAccountBalanceDTO.class);

        double balanceBefore = responseDTO1.getBalance();


        BankAccountAddMoneyDTO dto = new BankAccountAddMoneyDTO(accountId, sum);

        result = this.mockMvc.perform(patch(urlAddMoney)
                        .content(toJson(dto))
                        .contentType(contentTypeJson)
                        .accept(contentTypeJson))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();

        BankAccountBalanceDTO responseDTO = (BankAccountBalanceDTO) convertJSONStringToObject(
                result.getResponse().getContentAsString(), BankAccountBalanceDTO.class);

        assertNotNull(responseDTO.getCurrency());
        assertNotNull(responseDTO.getBalance());

        assertEquals((int) (responseDTO.getBalance() * 100) - (int) (balanceBefore * 100), (int) (sum * 100));
    }

    @Test
    void addMoneyIsNotFound() throws Exception{
        BankAccountAddMoneyDTO dto = new BankAccountAddMoneyDTO(-1,10.11);

        MvcResult result = this.mockMvc.perform(patch(urlAddMoney)
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

        MvcResult result = this.mockMvc.perform(patch(urlAddMoney)
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

        BankAccountAddMoneyDTO dto = new BankAccountAddMoneyDTO(1,-10.11);

        MvcResult result = this.mockMvc.perform(patch(urlAddMoney)
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

        BankAccountAddMoneyDTO dto = new BankAccountAddMoneyDTO(1,10.999);

        MvcResult result = this.mockMvc.perform(patch(urlAddMoney)
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