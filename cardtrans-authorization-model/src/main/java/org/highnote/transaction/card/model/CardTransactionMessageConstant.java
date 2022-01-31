package org.highnote.transaction.card.model;

/**
 * Utility class that holds all card transaction related constants.
 *
 * @author rimachavan
 */
public class CardTransactionMessageConstant {
    public static final int START_INDEX_MESSAGE = 0;
    public static final int INDEX_FOR_ZIPCODE = 2;
    public static final int INDEX_FOR_CARDHOLDER_NAME = 3;
    public static final int INDEX_FOR_RESPONSE_CODE = 4;
    public static final int INDEX_FOR_TRANS_AMOUNT = 5;
    public static final int INDEX_FOR_EXPIRY_DATE = 6;
    public static final int INDEX_FOR_PAN = 7;

    public static final int LENGTH_OF_MESSAGE_TYPE = 4;
    public static final int LENGTH_OF_DATA_FIELDS = 2;
    public static final int LENGTH_OF_LLVAR = 2;
    public static final int LENGTH_OF_EXPIRY = 4;
    public static final int LENGTH_OF_TRANS_AMOUNT = 10;
    public static final int LENGTH_OF_ZIPCODE = 5;
}
