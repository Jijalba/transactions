package com.mendel.transactions.domain.interfaces;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;

/**
 * Service responsible for updating a Transaction.
 */
public interface IUpdateTransactionService {

    /**
     * Execute method for the service
     *
     * @param transaction The transaction to be updated.
     * @return The updated transaction.
     */
    public Transaction execute(Transaction transaction) throws ServiceException;
}
