package com.mendel.transactions.application.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.transactions.application.TransactionDTO;
import com.mendel.transactions.application.TransactionMapper;
import com.mendel.transactions.domain.interfaces.ICreateTransactionService;
import com.mendel.transactions.domain.interfaces.ITransactionsRepository;
import com.mendel.transactions.domain.interfaces.IUpdateTransactionService;
import com.mendel.transactions.helper.TransactionCreator;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITransactionsRepository transactionsRepository;

    @MockBean
    private ICreateTransactionService createTransactionService;

    @MockBean
    private IUpdateTransactionService updateTransactionService;

    private static final Long ID = 1L;
    private static final String ERROR_MESSAGE = "ERROR";

    @Test
    public void getAllTransactionsOk() throws Exception {
        when(transactionsRepository.get()).thenReturn(TransactionCreator.aTransactionList());

        var response = mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = response.getResponse().getContentAsString();
        List<TransactionDTO> result = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() > 0);

    }

    @Test
    public void getTransactionByIdOk() throws Exception {
        var aTransaction = TransactionCreator.aTransaction();
        when(transactionsRepository.get(aTransaction))
                .thenReturn(aTransaction);

        var response = mockMvc.perform(get("/api/transactions/{id}", ID))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = response.getResponse().getContentAsString();
        TransactionDTO result = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });

        Assertions.assertEquals(aTransaction.getAmount(), result.getAmount());
        Assertions.assertEquals(aTransaction.getType(), result.getType());
        Assertions.assertEquals(aTransaction.getParentId(), result.getParentId());

    }

    @Test
    public void getTransactionByIdNotFound() throws Exception {
        when(transactionsRepository.get(TransactionCreator.aTransaction())).thenReturn(null);

        mockMvc.perform(get("/api/transactions/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No transactions found for id: " + ID));
    }

    @Test
    public void postTransactionOk() throws Exception {
        var aTransaction = TransactionCreator.aTransaction();
        var aDto = TransactionMapper.toTransactionDTO(aTransaction);

        when(createTransactionService.execute(any())).thenReturn(aTransaction);

        var response = mockMvc.perform(
                        post("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper()
                                        .writeValueAsString(aDto)))
                .andExpect(status().isCreated())
                .andReturn();

        var responseBody = response.getResponse().getContentAsString();
        Map<Long, TransactionDTO> result = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });

        Assertions.assertEquals(1, result.size());
        Assertions.assertNotNull(result.get(aTransaction.getId()));
        var dto = result.get(aTransaction.getId());
        Assertions.assertEquals(aTransaction.getType(), dto.getType());
        Assertions.assertEquals(aTransaction.getParentId(), dto.getParentId());

    }

    @Test
    public void postTransactionServiceException() throws Exception {
        var aTransaction = TransactionCreator.aTransaction();
        var aDto = TransactionMapper.toTransactionDTO(aTransaction);

        when(createTransactionService.execute(any())).thenThrow(new ServiceException(ERROR_MESSAGE));

        var response = mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(aDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var responseBody = response.getResponse().getContentAsString();
        Assertions.assertEquals(ERROR_MESSAGE, responseBody);
    }

    @Test
    public void putTransactionOk() throws Exception {
        var aTransaction = TransactionCreator.aTransaction();
        var aDto = TransactionMapper.toTransactionDTO(aTransaction);

        when(updateTransactionService.execute(any())).thenReturn(aTransaction);

        var response = mockMvc.perform(
                        put("/api/transactions/"+ ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper()
                                        .writeValueAsString(aDto)))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = response.getResponse().getContentAsString();
        Map<String, String> result = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("ok", result.get("status"));
    }

    @Test
    public void putTransactionServiceException() throws Exception {
        var aTransaction = TransactionCreator.aTransaction();
        var aDto = TransactionMapper.toTransactionDTO(aTransaction);

        when(updateTransactionService.execute(any())).thenThrow(new ServiceException(ERROR_MESSAGE));

        var response = mockMvc.perform(put("/api/transactions/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(aDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var responseBody = response.getResponse().getContentAsString();
        Assertions.assertEquals(ERROR_MESSAGE, responseBody);
    }
}
