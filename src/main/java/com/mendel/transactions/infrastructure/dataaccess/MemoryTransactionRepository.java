package com.mendel.transactions.infrastructure.dataaccess;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.interfaces.ITransactionsRepository;
import com.mendel.transactions.infrastructure.exceptions.RepositoryException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class MemoryTransactionRepository implements ITransactionsRepository {

    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    @Override
    public Transaction get(Transaction transaction) {
        return transactions.stream()
                .filter(t -> t.getId().equals(transaction.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Transaction add(Transaction transaction) throws RepositoryException {
        var existentTransaction = transactions.stream()
                .filter(t -> t.getId().equals(transaction.getId()))
                .findFirst()
                .orElse(null);

        if (existentTransaction != null)
            throw new RepositoryException("The transaction already exists in the repository.");

        transactions.add(transaction);
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) throws RepositoryException {
        var index = transactions.indexOf(transaction);

        if (index == -1)
            throw new RepositoryException("The transaction doesn't exist in the repository.");

        transactions.set(index, transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findByType(String type) {
        return transactions.stream()
                .filter(t -> t.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByParentId(Long parentId) {
        return transactions.stream()
                .filter(t -> t.getParentId().equals(parentId))
                .collect(Collectors.toList());
    }
}
