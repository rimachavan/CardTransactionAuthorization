package org.highnote.transaction.service;

import org.highnote.transaction.api.TransactionAuthRequest;
import org.highnote.transaction.api.TransactionMessageType;
import org.highnote.transaction.card.model.CardTransactionMessageConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.BitSet;


public class CardTransactionValidatorTest {

    private CardTransactionValidator validator;
    private static final String VALID_MSG = "0100e016411111111111111112250000001000";

    @Before
    public void init(){
        validator = new CardTransactionValidator();
    }

    @Test
    public void validate_whenIncomingMessageIsResponseType() {
        TransactionAuthRequest request = createFakeRequestWithInvalidMessageType();
        Assert.assertFalse(validator.validate(request));
    }

    @Test
    public void validate_whenIncomingMessageIsRequestType() {
        TransactionAuthRequest request = createValidAuthRequest();
        Assert.assertTrue(validator.validate(request));
    }

    @Test
    public void validate_whenMandatoryFieldPanIsAbsent() {
        TransactionAuthRequest request = createFakeRequestWithNoPanInDataField();
        Assert.assertFalse(validator.validate(request));
    }

    @Test
    public void validate_whenMandatoryFieldExpiryDateIsAbsent() {
        TransactionAuthRequest request = createFakeRequestWithNoExpiryDateInDataField();
        Assert.assertFalse(validator.validate(request));
    }

    @Test
    public void validate_whenMandatoryFieldTransAmtIsAbsent() {
        TransactionAuthRequest request = createFakeRequestWithNoTransAmtInDataField();
        Assert.assertFalse(validator.validate(request));
    }

    private TransactionAuthRequest createFakeRequestWithInvalidMessageType() {
        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getTransactionMessageType()).thenReturn(TransactionMessageType.UNKNOWN);
        return request;
    }

    private TransactionAuthRequest createFakeRequestWithNoPanInDataField() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_TRANS_AMOUNT);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_EXPIRY_DATE);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getTransactionMessageType()).thenReturn(TransactionMessageType.REQUEST);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        return request;
    }

    private TransactionAuthRequest createFakeRequestWithNoExpiryDateInDataField() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_TRANS_AMOUNT);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_PAN);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getTransactionMessageType()).thenReturn(TransactionMessageType.REQUEST);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        return request;
    }

    private TransactionAuthRequest createFakeRequestWithNoTransAmtInDataField() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_EXPIRY_DATE);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_PAN);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getTransactionMessageType()).thenReturn(TransactionMessageType.REQUEST);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        return request;
    }

    private TransactionAuthRequest createValidAuthRequest() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_EXPIRY_DATE);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_PAN);
        bitSet.set(CardTransactionMessageConstant.INDEX_FOR_TRANS_AMOUNT);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getTransactionMessageType()).thenReturn(TransactionMessageType.REQUEST);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        return request;
    }
}