package org.highnote.transaction.service;

import org.highnote.transaction.api.TransactionAuthResponse;
import org.highnote.transaction.api.TransactionWriter;

public class CardTransactionWriter implements TransactionWriter {

    @Override
    public void write(TransactionAuthResponse authResponse) {
        System.out.println(authResponse.getOutputMessage());
    }
}
