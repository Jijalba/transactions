package com.mendel.transactions.domain.interfaces;

import java.math.BigDecimal;

/**
 * Service responsible for getting the sum amount of all transaction from a parent transaction.
 */
public interface IGetChildrenAmountService {

    /**
     * Execute method for the service.
     * @param parentTransactionId The Transaction ID from the parent to retrieve.
     * @return The sum of all amounts of children.
     */
    public BigDecimal execute(Long parentTransactionId);
}
