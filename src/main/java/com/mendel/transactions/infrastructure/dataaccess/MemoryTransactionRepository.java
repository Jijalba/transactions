package com.mendel.transactions.infrastructure.dataaccess;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.entities.TransactionBuilder;
import com.mendel.transactions.domain.interfaces.ITransactionsRepository;
import com.mendel.transactions.infrastructure.exceptions.RepositoryException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Memory Repository for transactions.
 */
@Repository
public class MemoryTransactionRepository implements ITransactionsRepository {

    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    /**
     * Retrieves all transaction from the repository.
     *
     * @return The transactions.
     */
    @Override
    public List<Transaction> get() {
        return transactions;
    }

    /**
     * Retrieves a transaction from the repository based on its ID.
     *
     * @param transaction The transaction containing the ID to retrieve.
     * @return The transaction with the specified ID, or null if it does not exist.
     */
    @Override
    public Transaction get(Transaction transaction) {
        return transactions.stream()
                .filter(t -> t.getId().equals(transaction.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * adds a transaction to the repository.
     *
     * @param transaction The transaction to add.
     * @return The added transaction, with an ID assigned by the repository.
     */
    @Override
    public Transaction add(Transaction transaction) throws RepositoryException {

        var maxId = transactions.stream()
                .map(Transaction::getId)
                .max(Long::compareTo)
                .orElse(0L);

        var newTransaction = new TransactionBuilder()
                .withId(maxId + 1)
                .withAmount(transaction.getAmount())
                .withType(transaction.getType())
                .withParentId(transaction.getParentId())
                .build();

        transactions.add(newTransaction);
        return newTransaction;
    }

    /**
     * updates a transaction to the repository.
     *
     * @param transaction The transaction to update.
     * @return The updated transaction.
     */
    @Override
    public Transaction update(Transaction transaction) throws RepositoryException {
        var index = transactions.indexOf(transaction);

        if (index == -1)
            throw new RepositoryException("The transaction doesn't exist in the repository.");

        transactions.set(index, transaction);
        return transaction;
    }

    /**
     * Finds all transactions of a certain type.
     *
     * @param type The type of transaction to find.
     * @return A list of transactions of the specified type.
     */
    @Override
    public List<Transaction> findByType(String type) {
        return transactions.stream()
                .filter(t -> t.getType().equals(type))
                .collect(Collectors.toList());
    }

    /**
     * Finds all transactions with a specific parent transaction ID.
     *
     * @param parentTransactionId The parent transaction ID to search for.
     * @return A list of transactions with the specified parent transaction ID.
     */
    @Override
    public List<Transaction> findByParentId(Long parentTransactionId) {
        return transactions.stream()
                .filter(t -> t.getParentId() != null)
                .filter(t -> t.getParentId().equals(parentTransactionId))
                .collect(Collectors.toList());
    }
}
