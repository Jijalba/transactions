package com.mendel.transactions.domain.interfaces;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;

/**
 * Service responsible for creating a new Transaction.
 */
public interface ICreateTransactionService {

    /**
     * Execute method for the service
     *
     * @param transaction The transaction to be created.
     * @return The created transaction.
     */
    public Transaction execute(Transaction transaction) throws ServiceException;
}
