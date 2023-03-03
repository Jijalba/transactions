package com.mendel.transactions.application;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.entities.TransactionBuilder;

/**
 * A Helper class to map from Dto to Model object.
 */
public class TransactionMapper {

    public static Transaction toTransaction(Long id, TransactionDTO transactionDTO) {
        return new TransactionBuilder()
                .withId(id)
                .withType(transactionDTO.getType())
                .withAmount(transactionDTO.getAmount())
                .withParentId(transactionDTO.getParentId())
                .build();

    }

    public static TransactionDTO toTransactionDTO(Transaction transaction) {
        return new TransactionDTOBuilder()
                .withType(transaction.getType())
                .withAmount(transaction.getAmount())
                .withParentId(transaction.getParentId())
                .build();
    }
}