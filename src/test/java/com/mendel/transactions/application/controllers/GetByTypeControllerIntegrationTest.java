package com.mendel.transactions.application.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.interfaces.IGetByTypeService;
import com.mendel.transactions.helper.TransactionCreator;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetByTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IGetByTypeService getByTypeService;

    private static final String TEST_TYPE = "test";
    private static final String ERROR_MESSAGE = "error message";

    @Test
    void testOk() throws Exception {
        var transactions = TransactionCreator.aTransactionList();
        when(getByTypeService.execute(TEST_TYPE)).thenReturn(transactions);

        var result = mockMvc.perform(get("/api/transactions/types/" + TEST_TYPE))
                .andExpect(status().isOk())
                .andReturn();

        var expectedResponseBody = transactions.stream()
                .map(Transaction::getId)
                .collect(Collectors.toList());

        var responseBody = result.getResponse().getContentAsString();
        List<Long> ids = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });

        Assertions.assertEquals(expectedResponseBody, ids);
    }

    @Test
    void testNotFound() throws Exception {
        when(getByTypeService.execute(TEST_TYPE)).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/api/transactions/types/" + TEST_TYPE))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals("No transactions found for type: " + TEST_TYPE, result.getResponse().getContentAsString());
    }

    @Test
    void testServiceException() throws Exception {

        when(getByTypeService.execute(TEST_TYPE)).thenThrow(new ServiceException(ERROR_MESSAGE));

        MvcResult result = mockMvc.perform(get("/api/transactions/types/" + TEST_TYPE))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertEquals(ERROR_MESSAGE, result.getResponse().getContentAsString());
    }
}