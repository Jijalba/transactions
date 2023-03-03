package com.mendel.transactions.application.controllers;

import com.mendel.transactions.application.TransactionDTO;
import com.mendel.transactions.application.TransactionMapper;
import com.mendel.transactions.domain.interfaces.*;
import com.mendel.transactions.domain.entities.TransactionBuilder;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for transactions.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final ICreateTransactionService createTransactionService;
    private final IUpdateTransactionService updateTransactionService;
    private final ITransactionsRepository transactions;

    public TransactionController(ICreateTransactionService createTransactionService,
                                 IUpdateTransactionService updateTransactionService,
                                 ITransactionsRepository transactions) {
        this.createTransactionService = createTransactionService;
        this.updateTransactionService = updateTransactionService;
        this.transactions = transactions;
    }


    /**
     * Gets all transactions.
     *
     * @return The list of transactions in the repository.
     */
    @GetMapping()
    public ResponseEntity<List<TransactionDTO>> getTransactions() {

        var responseContent = transactions.get()
                .stream()
                .map(TransactionMapper::toTransactionDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseContent, HttpStatus.OK);
    }

    /**
     * Gets a transaction with a specific id.
     * @param id The id to look for.
     * @return The list of transactions in the repository.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {

        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must provide an id.");

        var transaction = new TransactionBuilder()
                .withId(id)
                .build();

        transaction = transactions.get(transaction);
        if (transaction == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions found for id: " + id);

        return ResponseEntity.ok(
                TransactionMapper.toTransactionDTO(transaction));
    }

    /**
     * Creates a transaction.
     * @param transactionDTO The new transaction.
     * @return A Map of {id: newTransaction}.
     */
    @PostMapping()
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {

        try {
            var newTransaction = createTransactionService.execute(TransactionMapper.toTransaction(null, transactionDTO));
            var dto = TransactionMapper.toTransactionDTO(newTransaction);
            var responseContent = Map.of(newTransaction.getId(), dto);

            return new ResponseEntity<>(responseContent, HttpStatus.CREATED);

        } catch (ServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    /**
     * Updates a transaction.
     * @param id The id of the Transaction to be updated.
     * @param transactionDTO The body to be updated.
     * @return A Map of {id: newTransaction}.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id,
                                               @RequestBody TransactionDTO transactionDTO) {

        try {
            var newTransaction = updateTransactionService.execute(TransactionMapper.toTransaction(id, transactionDTO));
            return ResponseEntity.ok(Map.of("status", "ok"));

        } catch (ServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
