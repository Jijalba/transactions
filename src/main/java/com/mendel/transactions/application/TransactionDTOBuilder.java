package com.mendel.transactions.application;

import java.math.BigDecimal;

public class TransactionDTOBuilder {

    private final TransactionDTO transactionDTO;

    public TransactionDTOBuilder() {
        this.transactionDTO = new TransactionDTO();
    }

    public TransactionDTOBuilder withType(String type) {
        this.transactionDTO.setType(type);
        return this;
    }

    public TransactionDTOBuilder withAmount(BigDecimal amount) {
        this.transactionDTO.setAmount(amount);
        return this;
    }

    public TransactionDTOBuilder withParentId(Long parentId) {
        this.transactionDTO.setParentId(parentId);
        return this;
    }

    public TransactionDTO build() {
        return this.transactionDTO;
    }
}
