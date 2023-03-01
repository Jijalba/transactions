package com.mendel.transactions.domain.interfaces;

import com.mendel.transactions.domain.entities.Transaction;

import java.util.List;

/**
 * Service responsible for getting all the transaction with a specific type.
 */
public interface IGetByTypeService {

    /**
     * Execute method for the service.
     * @param type  The type of transactions to retrieve.
     * @return The list of transactions with the specific type.
     */
    public List<Transaction> execute(String type);
}
