package com.mendel.transactions.application.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.transactions.domain.interfaces.IGetChildrenAmountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetSumByParentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IGetChildrenAmountService getChildrenAmountService;

    private static final Long PARENT_ID = 1L;
    private static final BigDecimal SUM = BigDecimal.valueOf(1);
    private static final String ERROR_MESSAGE = "error message";


    @Test
    void testOk() throws Exception {
        when(getChildrenAmountService.execute(PARENT_ID))
                .thenReturn(SUM);

        var response = mockMvc.perform(get("/api/transactions/sum/" + PARENT_ID))
                .andExpect(status().isOk())
                .andReturn();
        var expectedResponseBody = Map.of("sum", SUM);

        var responseBody = response.getResponse().getContentAsString();
        Map<String, BigDecimal> result = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });

        Assertions.assertEquals(expectedResponseBody, result);
    }

}