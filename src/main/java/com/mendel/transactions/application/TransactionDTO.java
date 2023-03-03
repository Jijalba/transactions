package com.mendel.transactions.application;

import java.math.BigDecimal;

/**
 * Api IO Data transfer object.
 */
public class TransactionDTO {
    private BigDecimal amount;
    private String type;
    private Long parentId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}