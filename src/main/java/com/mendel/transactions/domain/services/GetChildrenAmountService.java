package com.mendel.transactions.domain.services;

import com.mendel.transactions.domain.interfaces.IGetChildrenAmountService;
import com.mendel.transactions.domain.interfaces.ITransactionsRepository;
import com.mendel.transactions.infrastructure.exceptions.ServiceException;
import com.mendel.transactions.domain.entities.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsible for getting the sum amount of all transaction from a parent transaction.
 */
@Service
public class GetChildrenAmountService implements IGetChildrenAmountService {

    private final ITransactionsRepository transactions;

    public GetChildrenAmountService(ITransactionsRepository transactions) {
        this.transactions = transactions;
    }

    /**
     * Execute method for the service.
     *
     * @param parentTransactionId The Transaction ID from the parent to retrieve.
     * @return The sum of all amounts of children.
     */
    @Override
    public BigDecimal execute(Long parentTransactionId) {
        return transactions.findByParentId(parentTransactionId).stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
