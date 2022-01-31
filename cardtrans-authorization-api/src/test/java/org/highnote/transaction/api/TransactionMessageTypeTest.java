package org.highnote.transaction.api;

import org.junit.Assert;
import org.junit.Test;

public class TransactionMessageTypeTest {
    private static String REQUEST_MSG_VALUE = "0100";
    private static String RESPONSE_MSG_VALUE = "0110";
    private static String INVALID_MSG_VALUE = "1111";
    private static String EMPTY_MSG_VALUE = "";
    private static String NULL_MSG_VALUE = null;

    @Test
    public void testGetValue() {
        Assert.assertEquals(TransactionMessageType.REQUEST.getValue(), REQUEST_MSG_VALUE);
        Assert.assertEquals(TransactionMessageType.RESPONSE.getValue(), RESPONSE_MSG_VALUE);
    }

    @Test
    public void testFindByValue() {
        Assert.assertEquals(TransactionMessageType.findByValue(REQUEST_MSG_VALUE), TransactionMessageType.REQUEST);
        Assert.assertEquals(TransactionMessageType.findByValue(RESPONSE_MSG_VALUE), TransactionMessageType.RESPONSE);
        Assert.assertEquals(TransactionMessageType.findByValue(INVALID_MSG_VALUE), TransactionMessageType.UNKNOWN);
        Assert.assertEquals(TransactionMessageType.findByValue(EMPTY_MSG_VALUE), TransactionMessageType.UNKNOWN);
        Assert.assertEquals(TransactionMessageType.findByValue(NULL_MSG_VALUE), TransactionMessageType.UNKNOWN);
    }

    @Test
    public void testValues() {
        Assert.assertEquals(TransactionMessageType.values().length, 3);
    }
}