package com.mendel.transactions.domain.interfaces;

import com.mendel.transactions.domain.entities.Transaction;

import java.util.List;

/**
 * Repository interface for transactions.
 */
public interface ITransactionsRepository {

    /**
     * Retrieves a transaction from the repository based on its ID.
     * @param transaction The transaction containing the ID to retrieve.
     * @return The transaction with the specified ID, or null if it does not exist.
     */
    Transaction get(Transaction transaction);

    /**
     * Saves a transaction to the repository.
     * @param transaction The transaction to save.
     * @return The saved transaction, with an ID assigned by the repository.
     */
    Transaction save(Transaction transaction);

    /**
     * Finds all transactions of a certain type.
     * @param type The type of transaction to find.
     * @return A list of transactions of the specified type.
     */
    List<Transaction> findByType(String type);

    /**
     * Finds all transactions with a specific parent transaction ID.
     * @param parentTransactionId The parent transaction ID to search for.
     * @return A list of transactions with the specified parent transaction ID.
     */
    List<Transaction> findByParentId(Long parentTransactionId);
}
