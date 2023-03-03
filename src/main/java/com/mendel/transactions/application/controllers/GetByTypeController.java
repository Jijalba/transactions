package com.mendel.transactions.application.controllers;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.interfaces.IGetByTypeService;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * Handles HTTP requests for retrieving transactions of a particular type.
 * <p>
 * It maps to the base URL endpoint "/api/transactions/types/".
 */
@RestController
@RequestMapping("/api/transactions/types/")
public class GetByTypeController {
    private final IGetByTypeService getByTypeService;

    public GetByTypeController(IGetByTypeService getByTypeService) {
        this.getByTypeService = getByTypeService;
    }


    /**
     * Gets all the Transactions Ids from a type.
     *
     * @param type The type to look for.
     * @return A list of Longs with the Ids.
     */
    @GetMapping("/{type}")
    public ResponseEntity<?> getByType(@PathVariable String type) {

        try {
            var transactions = getByTypeService.execute(type);

            if (transactions == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions found for type: " + type);

            var responseContent = transactions
                    .stream()
                    .map(Transaction::getId)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responseContent);

        } catch (ServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
