package org.highnote.transaction.api;

import java.util.BitSet;

/**
 * Interface that represents an incoming trasaction request for authorization.
 *
 * @author rimachavan
 */
public interface TransactionAuthRequest {

    /**
     * Returns original incoming transaction message.
     *
     * @return transactionMessage
     */
    String getIncomingTransactionMessage();

    /**
     * Returns transaction type for incoming message, see {@link TransactionMessageType}.
     *
     * @return transactionMessageType
     */
    TransactionMessageType getTransactionMessageType();

    /**
     * Returns bitset corresponding to data fields available in incoming message.
     *
     * @return bitset
     */
    BitSet getDataFields();

    /**
     * Returns expiry of card.
     *
     * @return expiry
     */
    int getExpiryDate();

    /**
     * Returns transaction amount in cents.
     *
     * @return transactionAmount
     */
    long getTransactionAmount();

    /**
     * Returns zip code of the transaction.
     *
     * @return zipCode
     */
    int getZipCode();

    /**
     * Returns last index of transaction message, this helps to identify where to append response code.
     *
     * @return index
     */
    int getLastIndexOfTransaction();

    /**
     * Returns PAN present in the transaction.
     *
     * @return pan
     */
    String getPan();

    /**
     * Returns card holders name present in the transaction.
     *
     * @return name
     */
    String getCardHolderName();
}
