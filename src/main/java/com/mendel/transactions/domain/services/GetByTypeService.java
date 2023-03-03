package com.mendel.transactions.domain.services;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.interfaces.IGetByTypeService;
import com.mendel.transactions.domain.interfaces.ITransactionsRepository;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for getting all the transaction with a specific type.
 */
@Service
public class GetByTypeService implements IGetByTypeService {

    private final ITransactionsRepository transactions;

    public GetByTypeService(ITransactionsRepository transactions) {
        this.transactions = transactions;
    }

    /**
     * Execute method for the service.
     *
     * @param type The type of transactions to retrieve.
     * @return The list of transactions with the specific type.
     */
    @Override
    public List<Transaction> execute(String type) throws ServiceException {

        if (type == null)
            throw new ServiceException("The type should not be null.");

        return transactions.findByType(type);
    }
}
