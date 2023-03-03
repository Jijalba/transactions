package com.mendel.transactions.application.controllers;

import com.mendel.transactions.domain.interfaces.IGetChildrenAmountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Handles HTTP requests for calculating the sum of all transactions
 * <p>
 * for a given parent transaction ID. It maps to the base URL endpoint "/api/transactions/sum".
 */
@RestController
@RequestMapping("/api/transactions/sum")
public class GetSumByParentController {

    private final IGetChildrenAmountService getChildrenAmountService;

    public GetSumByParentController(IGetChildrenAmountService getChildrenAmountService) {
        this.getChildrenAmountService = getChildrenAmountService;
    }


    /**
     * Get the sum of all children transactions from a Parent id.
     *
     * @param parentId The Parent id.
     * @return A map of "sum:amount"
     */
    @GetMapping("/{parentId}")
    public ResponseEntity<?> getSum(@PathVariable Long parentId) {
        return ResponseEntity.ok(Map.of("sum", getChildrenAmountService.execute(parentId)));
    }
}
