package org.highnote.transaction.api;

/**
 * Interface that represents transaction authorization response.
 *
 * @author rimachavan
 */
public interface TransactionAuthResponse {

    /**
     * Returns output response message.
     *
     * @return responseMessage
     */
    String getOutputMessage();

    /**
     * Returns response code indication if authorization was success, denied or erroneous.
     *
     * @return responseCode
     */
    ResponseCode getResponseCode();
}
