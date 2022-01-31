package org.highnote.transaction.card.model;

import org.highnote.transaction.api.TransactionMessageType;
import org.highnote.transaction.api.ResponseCode;
import org.highnote.transaction.api.TransactionAuthRequest;
import org.highnote.transaction.api.TransactionAuthResponse;

import javax.xml.bind.DatatypeConverter;
import java.util.BitSet;
import java.util.Locale;

/**
 * This class represents a card transaction response message.
 *
 * @author rimachavan
 */
public final class CardTransactionAuthResponse implements TransactionAuthResponse {
    private final ResponseCode responseCode;
    private final TransactionAuthRequest authRequest;
    private final String outgoingMessage;

    private CardTransactionAuthResponse(TransactionAuthRequest authRequest, ResponseCode responseCode,
                                        String outgoingMessage) {
        this.responseCode = responseCode;
        this.authRequest = authRequest;
        this.outgoingMessage = outgoingMessage;
    }

    /**
     * Returns output response message ready to publish.
     *
     * @return outputMessage
     */
    @Override
    public String getOutputMessage() {
        return this.outgoingMessage;
    }

    /**
     * Returns card transaction response code. See {@link ResponseCode}
     *
     * @return responseCode
     */
    @Override
    public ResponseCode getResponseCode() {
        return responseCode;
    }

    /**
     * Static Builder class helps to build response message.
     */
    public static class Builder{
        private final TransactionAuthRequest authRequest;
        private final ResponseCode responseCode;
        private StringBuilder outgoingMsgBuilder;
        private BitSet dataFields;

        public Builder(TransactionAuthRequest authRequest, ResponseCode responseCode) {
            this.responseCode = responseCode;
            this.authRequest = authRequest;
        }

        /**
         * Builds card transaction response message
         *
         * @return transactionResponse
         */
        public TransactionAuthResponse build() {
            String outgoingMessage = generateOutgoingMessage();
            return new CardTransactionAuthResponse(this.authRequest, this.responseCode, outgoingMessage);
        }

        /**
         * Helps to generate resultant outgoing response message
         *
         * @return outputMessage
         */
        private String generateOutgoingMessage() {
            setResponseMessageType();
            setDataFields();
            setResponseCode();

            return outgoingMsgBuilder.toString();
        }

        /**
         * Helps to set response message type
         */
        private void setResponseMessageType() {
            this.outgoingMsgBuilder = new StringBuilder(this.authRequest.getIncomingTransactionMessage());
            outgoingMsgBuilder.replace(CardTransactionMessageConstant.START_INDEX_MESSAGE, CardTransactionMessageConstant.LENGTH_OF_MESSAGE_TYPE, TransactionMessageType.RESPONSE.getValue());
        }

        /**
         * Helps to set bit set which represents metadata or data fields present in response message.
         */
        private void setDataFields() {
            this.dataFields = this.authRequest.getDataFields();
            this.dataFields.set(CardTransactionMessageConstant.INDEX_FOR_RESPONSE_CODE);
            String responseDataField = DatatypeConverter.printHexBinary(this.dataFields.toByteArray());

            this.outgoingMsgBuilder.replace(CardTransactionMessageConstant.LENGTH_OF_MESSAGE_TYPE, CardTransactionMessageConstant.LENGTH_OF_MESSAGE_TYPE+ CardTransactionMessageConstant.LENGTH_OF_DATA_FIELDS,
                    responseDataField.toLowerCase(Locale.ROOT));
        }

        /**
         * Helps to set Response code
         */
        private void setResponseCode() {
            if(this.authRequest.getIncomingTransactionMessage().length() == this.authRequest.getLastIndexOfTransaction())
                this.outgoingMsgBuilder.append(this.responseCode);
            else
                this.outgoingMsgBuilder.insert(this.authRequest.getLastIndexOfTransaction(), this.responseCode);
        }
    }
}
