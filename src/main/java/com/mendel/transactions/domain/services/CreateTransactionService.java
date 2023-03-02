package com.mendel.transactions.domain.services;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.interfaces.ICreateTransactionService;
import com.mendel.transactions.domain.interfaces.ITransactionsRepository;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionService implements ICreateTransactionService {

    private final ITransactionsRepository transactions;

    public CreateTransactionService(ITransactionsRepository transactions) {
        this.transactions = transactions;
    }

    /**
     * Execute method for the service
     *
     * @param transaction The transaction to be created.
     * @return The created transaction.
     */
    @Override
    public Transaction execute(Transaction transaction) throws ServiceException {

        if (!transaction.IsValid())
            throw new ServiceException("The transaction to be created doesn't have all the required attributes.");

        return transactions.add(transaction);
    }
}
