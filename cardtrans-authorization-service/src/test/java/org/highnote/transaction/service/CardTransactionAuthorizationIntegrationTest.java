package org.highnote.transaction.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Integration test for Card Transaction Authorization Service
 */
public class CardTransactionAuthorizationIntegrationTest {
    private CardTransactionAuthorizationService app;
    private CardTransactionReader reader;
    private CardTransactionWriter writer;
    private CardTransactionValidator validator;
    private CardTransactionAuthorizer authorizer;

    private static final String TEST_FILE = "src/test/resources/CardTransTestFile.txt";

    @Before
    public void init(){
        reader = new CardTransactionReader();
        writer = new CardTransactionWriter();
        validator = new CardTransactionValidator();
        authorizer = new CardTransactionAuthorizer();
        app = new CardTransactionAuthorizationService(reader, writer, validator, authorizer);
    }

    @Test
    public void startAuthorization() {
        app.startAuthorization(TEST_FILE);

        List<String> outgoingMessages = app.getOutgoingMessages();
        Assert.assertEquals(outgoingMessages.size(), 5);
        Assert.assertEquals(outgoingMessages.get(0), "0110f016411111111111111112250000001000OK");
        Assert.assertEquals(outgoingMessages.get(1), "0110f016401288888888188112250000011000DE");
        Assert.assertEquals(outgoingMessages.get(2), "0110fc16510510510510510012250000011000OK11MASTER YODA90089");
        Assert.assertEquals(outgoingMessages.get(3), "0110f016411111111111111112180000001000DE");
        Assert.assertEquals(outgoingMessages.get(4), "01107012250000001000ER");
    }
}
