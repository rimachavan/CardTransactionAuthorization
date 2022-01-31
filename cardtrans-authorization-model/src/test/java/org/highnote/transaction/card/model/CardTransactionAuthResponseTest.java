package org.highnote.transaction.card.model;


import org.highnote.transaction.api.ResponseCode;
import org.highnote.transaction.api.TransactionAuthRequest;
import org.highnote.transaction.api.TransactionAuthResponse;
import org.junit.Assert;
import org.junit.Test;

public class CardTransactionAuthResponseTest {

    private TransactionAuthResponse response;

    private static String INCOMING_REQ1 = "0100ec1651051051051051001225000001100011MASTER YODA90089";
    private static String EXPECTED_RESPONSE1 = "0110fc16510510510510510012250000011000OK11MASTER YODA90089";

    private static String INCOMING_REQ2_WO_NAME = "0100e016411111111111111112250000001000";
    private static String EXPECTED_RESPONSE2_WO_NAME = "0110f016411111111111111112250000001000OK";

    @Test
    public void testGetters() {
        response = getFakeTransactionAuthResponse(INCOMING_REQ1);
        Assert.assertEquals(response.getOutputMessage(), EXPECTED_RESPONSE1);
        Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
    }


    @Test
    public void getResponseCode() {
        response = getFakeTransactionAuthResponse(INCOMING_REQ2_WO_NAME);
        Assert.assertEquals(response.getOutputMessage(), EXPECTED_RESPONSE2_WO_NAME);
        Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);

    }

    private TransactionAuthResponse getFakeTransactionAuthResponse(String incomingReq) {
        TransactionAuthRequest authRequest = new CardTransactionAuthRequest.Parser(incomingReq).parse();
        TransactionAuthResponse response = new CardTransactionAuthResponse.Builder(authRequest, ResponseCode.OK).build();
        return response;
    }
}