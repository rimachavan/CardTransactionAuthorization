package org.highnote.transaction.service;

import org.highnote.transaction.api.ResponseCode;
import org.highnote.transaction.api.TransactionAuthorizer;
import org.highnote.transaction.api.TransactionReader;
import org.highnote.transaction.api.TransactionValidator;
import org.highnote.transaction.api.TransactionWriter;
import org.highnote.transaction.api.TransactionAuthRequest;
import org.highnote.transaction.api.TransactionAuthResponse;
import org.highnote.transaction.card.model.CardTransactionAuthRequest;
import org.highnote.transaction.card.model.CardTransactionAuthResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Card Transaction Authorization Application to authorize card transactions.
 *
 * @author rimachavan
 */
public class CardTransactionAuthorizationService {

    private TransactionReader transactionReader;

    private TransactionWriter transactionWriter;

    private TransactionValidator transactionValidator;

    private TransactionAuthorizer transactionAuthorizer;

    private List<String> outgoingMessages = new ArrayList<>();

    public CardTransactionAuthorizationService(CardTransactionReader cardTransactionReader,
                                               CardTransactionWriter cardTransactionWriter,
                                               CardTransactionValidator cardTransactionValidator,
                                               CardTransactionAuthorizer cardTransactionAuthorizer) {
        this.transactionReader = cardTransactionReader;
        this.transactionWriter = cardTransactionWriter;
        this.transactionValidator = cardTransactionValidator;
        this.transactionAuthorizer = cardTransactionAuthorizer;
    }

    /**
     * Helps authorizing incoming raw messages
     *
     * @param fileName
     */
    public void startAuthorization(String fileName) {
        /*
         * Step 1: Read incoming transaction messages from File
         */
        List<String> transactions = transactionReader.read(fileName);

        /*
         * Step 2: Iterate through individual transaction messages
         */
        for (String transaction : transactions) {
            /*
             * Step 3: Build CardTransaction Authorization Request
             */
            TransactionAuthRequest authRequest = new CardTransactionAuthRequest.Parser(transaction).parse();

            /*
             * Step 4: Validate Authorization Request for Mandatory fields
             */
            TransactionAuthResponse transactionAuthResponse;

            if (transactionValidator.validate(authRequest)) {

                /*
                 * Step 5: Authorize transaction
                 */
                transactionAuthResponse = transactionAuthorizer.authorize(authRequest);
            } else {
                transactionAuthResponse = new CardTransactionAuthResponse.Builder(authRequest, ResponseCode.ER).build();
            }

            /*
             * Step 6: Print outgoing message
             */
            transactionWriter.write(transactionAuthResponse);
            outgoingMessages.add(transactionAuthResponse.getOutputMessage());
        }
    }

    /**
     * Returns list of outgoing response messages for Integration test
     *
     * @return
     */
    public List<String> getOutgoingMessages() {
        return this.outgoingMessages;
    }
}
