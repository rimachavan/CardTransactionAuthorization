package org.highnote.transaction.api;

/**
 * Interface that facilitates validation of a given transaction message.
 *
 * @author rimachavan
 */
public interface TransactionValidator {

    /**
     * Returns validation results of a given transaction message
     *
     * @param request transaction message
     * @return boolean
     */
    boolean validate(TransactionAuthRequest request);
}
