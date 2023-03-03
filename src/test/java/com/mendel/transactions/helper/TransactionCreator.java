package com.mendel.transactions.helper;

import com.mendel.transactions.domain.entities.Transaction;
import com.mendel.transactions.domain.entities.TransactionBuilder;

import java.util.Arrays;
import java.util.List;

public class TransactionCreator {
    private static final String TEST_TYPE = "test";

    public static Transaction aTransaction() {
        return new TransactionBuilder()
                .withId(1L)
                .withType(TEST_TYPE)
                .build();
    }

    public static List<Transaction> aTransactionList() {
        var firstTransaction = new TransactionBuilder()
                .withId(1L)
                .withType(TEST_TYPE)
                .build();

        var secondTransaction = new TransactionBuilder()
                .withId(2L)
                .withType(TEST_TYPE)
                .build();

        var thirdTransaction = new TransactionBuilder()
                .withId(3L)
                .withType(TEST_TYPE)
                .build();

        return Arrays.asList(
                firstTransaction,
                secondTransaction,
                thirdTransaction
        );

    }

}
