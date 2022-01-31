package org.highnote.transaction.card.model;

import org.highnote.transaction.api.TransactionMessageType;
import org.highnote.transaction.api.TransactionAuthRequest;

import javax.xml.bind.DatatypeConverter;
import java.util.BitSet;

/**
 * This class represents immutable card transaction request.
 *
 * @author rimachavan
 */
public final class CardTransactionAuthRequest implements TransactionAuthRequest {
    private String originalMessage;
    private TransactionMessageType transactionMessageType;
    private BitSet dataFields;
    private String pan;
    private int expiryDate;
    private long transactionAmount;
    private String cardHolderName;
    private int zipCode;
    private int lastIndexOfTransaction;

    private CardTransactionAuthRequest(String originalMessage, TransactionMessageType transactionMessageType,
                                      BitSet dataFields, String pan, int expiryDate, long transactionAmount,
                                      String cardHolderName, int zipCode, int lastIndexOfTransaction) {
        this.originalMessage = originalMessage;
        this.transactionMessageType = transactionMessageType;
        this.dataFields = dataFields;
        this.pan = pan;
        this.expiryDate = expiryDate;
        this.transactionAmount = transactionAmount;
        this.cardHolderName = cardHolderName;
        this.zipCode = zipCode;
        this.lastIndexOfTransaction = lastIndexOfTransaction;
    }

    @Override
    public String getIncomingTransactionMessage() {
        return originalMessage;
    }

    @Override
    public BitSet getDataFields() {
        return dataFields;
    }

    @Override
    public TransactionMessageType getTransactionMessageType() {
        return transactionMessageType;
    }

    @Override
    public String getPan() {
        return pan;
    }

    @Override
    public int getExpiryDate() {
        return expiryDate;
    }

    @Override
    public long getTransactionAmount() {
        return transactionAmount;
    }

    @Override
    public String getCardHolderName() {
        return cardHolderName;
    }

    @Override
    public int getZipCode() {
        return zipCode;
    }

    @Override
    public int getLastIndexOfTransaction() {
        return lastIndexOfTransaction;
    }

    /**
     * Static builder class that helps to build immutable card transaction request object.
     */
    public static class Parser {

        private String originalMessage;
        private TransactionMessageType transactionMessageType;
        private BitSet dataFields;
        private String pan;
        private int expiryDate;
        private long transactionAmount;
        private String cardHolderName;
        private int zipCode;
        private int indexOfNextElement = 0;
        private int lastIndexOfTransaction = 0;

        public Parser(String originalMessage){
            this.originalMessage = originalMessage;
        }

        /**
         * Builds Card transaction request from incoming transaction message string.
         *
         * @return transactionAuthRequest
         */
        public TransactionAuthRequest parse() {
            readTransactionMessageType();
            readDataFields();
            readPan();
            readExpiry();
            readTransactionAmount();
            readCardHolderName();
            readZipCode();

            return new CardTransactionAuthRequest(this.originalMessage,
                    this.transactionMessageType, this.dataFields, this.pan, this.expiryDate, this.transactionAmount,
                    this.cardHolderName, this.zipCode, this.lastIndexOfTransaction);
        }

        /**
         * Method that helps to read transaction message type from string message
         */
        private void readTransactionMessageType(){
            String incomingMessageType = this.originalMessage.substring(indexOfNextElement, indexOfNextElement+ CardTransactionMessageConstant.LENGTH_OF_MESSAGE_TYPE);
            this.transactionMessageType = TransactionMessageType.findByValue(incomingMessageType);
            indexOfNextElement += CardTransactionMessageConstant.LENGTH_OF_MESSAGE_TYPE;
        }

        /**
         * Method that helps to read bitset metadata for data fields present in a transaction.
         */
        private void readDataFields(){
            String incomingDataFields = this.originalMessage.substring(indexOfNextElement, indexOfNextElement+ CardTransactionMessageConstant.LENGTH_OF_DATA_FIELDS);
            this.dataFields = BitSet.valueOf(DatatypeConverter.parseHexBinary(incomingDataFields));
            indexOfNextElement += CardTransactionMessageConstant.LENGTH_OF_DATA_FIELDS;
        }

        /**
         * Method that helps to read PAN from transaction message string literal.
         */
        private void readPan() {
            if(this.dataFields.get(CardTransactionMessageConstant.INDEX_FOR_PAN)){
                int lengthOfPan = Integer.parseInt(this.originalMessage.substring(indexOfNextElement, indexOfNextElement+ CardTransactionMessageConstant.LENGTH_OF_LLVAR));
                indexOfNextElement += CardTransactionMessageConstant.LENGTH_OF_LLVAR;
                this.pan = this.originalMessage.substring(indexOfNextElement, indexOfNextElement+lengthOfPan);
                indexOfNextElement += lengthOfPan;
            }
        }

        /**
         * Method that helps to read expiry date of card from transaction message string literal.
         */
        private void readExpiry() {
            if(this.dataFields.get(CardTransactionMessageConstant.INDEX_FOR_EXPIRY_DATE)){
                this.expiryDate = Integer.parseInt(this.originalMessage.substring(indexOfNextElement, indexOfNextElement+ CardTransactionMessageConstant.LENGTH_OF_EXPIRY));
                indexOfNextElement += CardTransactionMessageConstant.LENGTH_OF_EXPIRY;
            }
        }

        /**
         * Method that helps to read transaction amount in cents from transaction message string literal.
         */
        private void readTransactionAmount() {
            if(this.dataFields.get(CardTransactionMessageConstant.INDEX_FOR_TRANS_AMOUNT)){
                this.transactionAmount = Long.parseLong(this.originalMessage.substring(indexOfNextElement, indexOfNextElement+ CardTransactionMessageConstant.LENGTH_OF_TRANS_AMOUNT));
                indexOfNextElement += CardTransactionMessageConstant.LENGTH_OF_TRANS_AMOUNT;
            }
            this.lastIndexOfTransaction = indexOfNextElement;
        }

        /**
         * Method that helps to read card holder's name from transaction message string literal
         */
        private void readCardHolderName() {
            if(this.dataFields.get(CardTransactionMessageConstant.INDEX_FOR_CARDHOLDER_NAME)){
                int lengthOfName = Integer.parseInt(this.originalMessage.substring(indexOfNextElement, indexOfNextElement+ CardTransactionMessageConstant.LENGTH_OF_LLVAR));
                indexOfNextElement += CardTransactionMessageConstant.LENGTH_OF_LLVAR;
                this.cardHolderName = this.originalMessage.substring(indexOfNextElement, indexOfNextElement+lengthOfName);
                indexOfNextElement += lengthOfName;
            }
        }

        /**
         * Method that helps to read zip code from transaction message string literal
         */
        private void readZipCode() {
            if(this.dataFields.get(CardTransactionMessageConstant.INDEX_FOR_ZIPCODE)){
                this.zipCode = Integer.parseInt(this.originalMessage.substring(indexOfNextElement, indexOfNextElement+ CardTransactionMessageConstant.LENGTH_OF_ZIPCODE));
                indexOfNextElement += CardTransactionMessageConstant.LENGTH_OF_ZIPCODE;
            }
        }
    }
}
