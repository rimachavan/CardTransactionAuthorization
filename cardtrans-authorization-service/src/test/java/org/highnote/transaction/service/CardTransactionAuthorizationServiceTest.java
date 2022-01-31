package org.highnote.transaction.service;

import org.highnote.transaction.api.TransactionAuthResponse;
import org.highnote.transaction.api.TransactionAuthorizer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class CardTransactionAuthorizationServiceTest {

    private CardTransactionAuthorizationService app;
    private CardTransactionReader reader;
    private CardTransactionWriter writer;
    private CardTransactionValidator validator;
    private CardTransactionAuthorizer authorizer;

    private static final String TEST_FILE = "src/test/resources/CardTransTestFile.txt";

    @Before
    public void init(){
        reader = Mockito.mock(CardTransactionReader.class);
        writer = Mockito.mock(CardTransactionWriter.class);
        validator = Mockito.mock(CardTransactionValidator.class);
        authorizer = Mockito.mock(CardTransactionAuthorizer.class);

        app = new CardTransactionAuthorizationService(reader, writer, validator, authorizer);
    }

    @Test
    public void startAuthorization() {
        TransactionAuthResponse response = Mockito.mock(TransactionAuthResponse.class);
        Mockito.when(reader.read(TEST_FILE)).thenReturn(fakeTransactions());
        Mockito.when(validator.validate(Mockito.any())).thenReturn(true);
        Mockito.when(authorizer.authorize(Mockito.any())).thenReturn(response);

        app.startAuthorization(TEST_FILE);

        Mockito.verify(reader).read(Mockito.any());
        Mockito.verify(validator, Mockito.times(2)).validate(Mockito.any());
        Mockito.verify(authorizer, Mockito.times(2)).authorize(Mockito.any());
        Mockito.verify(writer, Mockito.times(2)).write(Mockito.any());
    }

    private List<String> fakeTransactions() {
        List<String> list = new ArrayList<>();
        list.add("0100e016411111111111111112250000001000");
        list.add("0100e016401288888888188112250000011000");
        return list;
    }
}