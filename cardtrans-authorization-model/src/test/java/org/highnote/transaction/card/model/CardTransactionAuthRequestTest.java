package org.highnote.transaction.card.model;

import org.highnote.transaction.api.TransactionMessageType;
import org.highnote.transaction.api.TransactionAuthRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;

public class CardTransactionAuthRequestTest {

    private static final String VALID_MSG1 = "0100e016411111111111111112250000001000";
    private static BitSet DATA_FIELDS1 = new BitSet(8);
    private static final long TRANS_AMOUNT1 = 1000;
    private static final int EXPIRY_DT1 = 1225;
    private static final String PAN1 = "4111111111111111";

    private static final String VALID_MSG_ZIP_AND_NAME = "0100ec1651051051051051001225000001100011MASTER YODA90089";
    private static BitSet DATA_FIELDS2 = new BitSet(8);
    private static final String PAN2 = "5105105105105100";
    private static final long TRANS_AMOUNT2 = 11000;
    private static final int EXPIRY_DT2 = 1225;
    private static final String CARD_HOLDER_NAME2 = "MASTER YODA";
    private static final int ZIP_CODE2 = 90089;

    @Before
    public void init() {
        DATA_FIELDS1.set(5, 8);

        DATA_FIELDS2.set(5, 8);
        DATA_FIELDS2.set(2, 4);
    }

    @Test
    public void parse_whenValidMessageNoZipNoName(){
        TransactionAuthRequest request = new CardTransactionAuthRequest.Parser(VALID_MSG1).parse();

        Assert.assertEquals(request.getIncomingTransactionMessage(), VALID_MSG1);
        Assert.assertEquals(request.getTransactionMessageType(), TransactionMessageType.REQUEST);
        Assert.assertEquals(request.getDataFields(), DATA_FIELDS1);
        Assert.assertEquals(request.getTransactionAmount(), TRANS_AMOUNT1);
        Assert.assertEquals(request.getExpiryDate(), EXPIRY_DT1);
        Assert.assertEquals(request.getZipCode(), 0);
        Assert.assertEquals(request.getPan(), PAN1);
        Assert.assertNull(request.getCardHolderName());
        Assert.assertEquals(request.getLastIndexOfTransaction(), 38);
    }

    @Test
    public void parse_whenValidMessageWithZipAndName(){
        TransactionAuthRequest request = new CardTransactionAuthRequest.Parser(VALID_MSG_ZIP_AND_NAME).parse();

        Assert.assertEquals(request.getIncomingTransactionMessage(), VALID_MSG_ZIP_AND_NAME);
        Assert.assertEquals(request.getTransactionMessageType(), TransactionMessageType.REQUEST);
        Assert.assertEquals(request.getDataFields(), DATA_FIELDS2);
        Assert.assertEquals(request.getTransactionAmount(), TRANS_AMOUNT2);
        Assert.assertEquals(request.getExpiryDate(), EXPIRY_DT2);
        Assert.assertEquals(request.getZipCode(), ZIP_CODE2);
        Assert.assertEquals(request.getPan(), PAN2);
        Assert.assertEquals(request.getCardHolderName(), CARD_HOLDER_NAME2);
        Assert.assertEquals(request.getLastIndexOfTransaction(), 38);
    }

}