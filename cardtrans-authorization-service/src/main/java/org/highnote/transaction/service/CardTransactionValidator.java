package org.highnote.transaction.service;

import org.highnote.transaction.api.TransactionAuthRequest;
import org.highnote.transaction.api.TransactionValidator;
import org.highnote.transaction.api.TransactionMessageType;

import java.util.BitSet;

/**
 * Validates card transaction message.
 *
 * @author rimachavan
 */
public class CardTransactionValidator implements TransactionValidator {

    private static final BitSet MANDATORY_DATA_FIELDS = new BitSet(8);

    static {
        /**
         * Index for mandatory fields
         * 7 - PAN
         * 6 - EXPIRY
         * 5 - TRANSACTION AMOUNT
         * Resultant BitSet - 1110 0000 - e0
         */
        MANDATORY_DATA_FIELDS.set(5, 8);
    }

    @Override
    public boolean validate(TransactionAuthRequest request) {
        if(!validateRequestMessageTypeIndicator(request.getTransactionMessageType()))
            return false;

        if(!validateMandatoryDataFields(request.getDataFields()))
            return false;

        return true;
    }


    /**
     * Validates incoming request message type
     *
     * @param incomingMessageType
     * @return
     */
    private boolean validateRequestMessageTypeIndicator(TransactionMessageType incomingMessageType){
        if(!incomingMessageType.equals(TransactionMessageType.REQUEST))
            return false;
        return true;
    }

    /**
     * Validates mandatory data fields.
     * 7 - PAN
     * 6 - EXPIRY
     * 5 - TRANSACTION AMOUNT
     * Resultant BitSet - 1110 0000 - e0
     *
     * @param inputDataFields
     * @return
     */
    private boolean validateMandatoryDataFields(final BitSet inputDataFields) {
        BitSet temp = (BitSet) inputDataFields.clone();
        temp.and(MANDATORY_DATA_FIELDS);
        if(!temp.equals(MANDATORY_DATA_FIELDS))
            return false;

        return true;
    }
}
