package org.highnote.transaction.api;

/**
 * Interface facilitates authorization of transactions.
 *
 * @author rimachavan
 */
public interface TransactionAuthorizer {

    /**
     * Method that helps to authorize transaction
     *
     * @param request transaction authorization request
     * @return transactionAuthResponse
     */
    TransactionAuthResponse authorize(TransactionAuthRequest request);
}
