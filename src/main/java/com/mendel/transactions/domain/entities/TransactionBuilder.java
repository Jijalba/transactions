package com.mendel.transactions.domain.entities;

import java.math.BigDecimal;

public class TransactionBuilder {

    private Transaction transaction;

    public TransactionBuilder Builder() {
        transaction = new Transaction();
        return this;
    }

    public TransactionBuilder withId(Long id) {
        transaction.setId(id);
        return this;
    }

    public TransactionBuilder withType(String type) {
        transaction.setType(type);
        return this;
    }

    public TransactionBuilder withAmount(BigDecimal amount) {
        transaction.setAmount(amount);
        return this;
    }

    public TransactionBuilder withParentId(Long parentId) {
        transaction.setParentId(parentId);
        return this;
    }

    public Transaction Build() {
        return transaction;
    }
}
