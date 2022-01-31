package org.highnote.transaction.api;

import org.junit.Assert;
import org.junit.Test;

public class ResponseCodeTest {

    @Test
    public void values() {
        Assert.assertEquals(ResponseCode.values().length, 3);
    }
}