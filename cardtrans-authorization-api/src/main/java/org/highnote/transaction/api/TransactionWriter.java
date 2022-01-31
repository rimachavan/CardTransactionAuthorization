package org.highnote.transaction.api;

/**
 * Interface that facilitates writing transaction to a given output source.
 *
 * @author rimachavan
 */
public interface TransactionWriter {

    /**
     * Method that helps to write application generated transaction authorization response.
     *
     * @param authResponse
     */
    void write(TransactionAuthResponse authResponse);
}
