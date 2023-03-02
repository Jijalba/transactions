package com.mendel.transactions.domain.services;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.interfaces.ICreateTransactionService;
import com.mendel.transactions.domain.interfaces.ITransactionsRepository;
import com.mendel.transactions.domain.interfaces.IUpdateTransactionService;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import org.springframework.stereotype.Service;

/**
 * Service responsible for updating a Transaction.
 */
@Service
public class UpdateTransactionService implements IUpdateTransactionService {

    private final ITransactionsRepository transactions;

    public UpdateTransactionService(ITransactionsRepository transactions) {
        this.transactions = transactions;
    }

    /**
     * Execute method for the service
     *
     * @param transaction The transaction to be updated.
     * @return The updated transaction.
     */
    @Override
    public Transaction execute(Transaction transaction) throws ServiceException {

        if (!transaction.IsValid())
            throw new ServiceException("The transaction to be created doesn't have all the required attributes.");

        return transactions.update(transaction);
    }
}
