package org.highnote.transaction.service;

import org.highnote.transaction.api.TransactionAuthResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CardTransactionWriterTest {

    private CardTransactionWriter writer;

    private static final String FAKE_RESPONSE_MSG = "0110f016411111111111111112250000001000OK";

    @Before
    public void init(){
        writer = new CardTransactionWriter();
    }

    @Test
    public void write(){
        TransactionAuthResponse authResponse = Mockito.mock(TransactionAuthResponse.class);
        Mockito.when(authResponse.getOutputMessage()).thenReturn(FAKE_RESPONSE_MSG);

        writer.write(authResponse);

        Mockito.verify(authResponse, Mockito.times(1)).getOutputMessage();
    }

}