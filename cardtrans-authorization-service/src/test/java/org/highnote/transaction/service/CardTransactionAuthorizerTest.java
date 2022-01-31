package org.highnote.transaction.service;

import org.highnote.transaction.api.ResponseCode;
import org.highnote.transaction.api.TransactionAuthRequest;
import org.highnote.transaction.api.TransactionAuthResponse;
import org.highnote.transaction.card.model.CardTransactionAuthRequest;
import org.highnote.transaction.card.model.CardTransactionMessageConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.BitSet;

import static org.highnote.transaction.card.model.CardTransactionMessageConstant.*;

public class CardTransactionAuthorizerTest {
    private static final int FAKE_ZIPCODE = 90089;
    private static final long AMOUNT_GR_THAN_200 = 20002l;
    private static final long AMOUNT_LS_THAN_200 = 19902l;
    private static final long AMOUNT_LS_THAN_100 = 9900l;
    private static final int FAKE_EXPIRY_IN_FUTURE = 1222; //12/2022
    private static final int FAKE_EXPIRY_IN_PAST = 1220; //12/2022

    private CardTransactionAuthorizer authorizer;

    @Before
    public void init(){
        authorizer = new CardTransactionAuthorizer();
    }

    @Test
    public void authorize_whenZipCodePresentAndAmountGtThan200(){
        TransactionAuthRequest request = fakeRequestWithZipCodePresentAndAmountGtThan200();

        TransactionAuthResponse response = authorizer.authorize(request);

        Assert.assertEquals(response.getResponseCode(), ResponseCode.DE);
    }

    @Test
    public void authorize_whenZipCodePresentAndAmountLsThan200(){
        TransactionAuthRequest request = fakeRequestWithZipCodePresentAndAmountLsThan200();

        TransactionAuthResponse response = authorizer.authorize(request);

        Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
    }

    @Test
    public void authorize_whenZipCodeNotPresentAndAmountLsThan200(){
        TransactionAuthRequest request = fakeRequestWithZipCodeNotPresentAndAmountLsThan200();

        TransactionAuthResponse response = authorizer.authorize(request);

        Assert.assertEquals(response.getResponseCode(), ResponseCode.DE);
    }

    @Test
    public void authorize_whenZipCodeNotPresentAndAmountLsThan100(){
        TransactionAuthRequest request = fakeRequestWithZipCodeNotPresentAndAmountLsThan100();

        TransactionAuthResponse response = authorizer.authorize(request);

        Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
    }

    @Test
    public void authorize_whenExpiryDateInPast(){
        TransactionAuthRequest request = fakeRequestWithExpiryDatePastCurrent();

        TransactionAuthResponse response = authorizer.authorize(request);

        Assert.assertEquals(response.getResponseCode(), ResponseCode.DE);
    }

    @Test
    public void authorize_whenExpiryDateInFuture(){
        TransactionAuthRequest request = fakeRequestWithExpiryDateInFuture();

        TransactionAuthResponse response = authorizer.authorize(request);

        Assert.assertEquals(response.getResponseCode(), ResponseCode.OK);
    }

    private TransactionAuthRequest fakeRequestWithExpiryDateInFuture() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(INDEX_FOR_ZIPCODE);
        bitSet.set(INDEX_FOR_TRANS_AMOUNT);
        bitSet.set(INDEX_FOR_EXPIRY_DATE);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        Mockito.when(request.getZipCode()).thenReturn(FAKE_ZIPCODE);
        Mockito.when(request.getTransactionAmount()).thenReturn(AMOUNT_LS_THAN_100);
        Mockito.when(request.getIncomingTransactionMessage()).thenReturn(new String());
        Mockito.when(request.getExpiryDate()).thenReturn(FAKE_EXPIRY_IN_FUTURE);
        return request;
    }

    private TransactionAuthRequest fakeRequestWithExpiryDatePastCurrent() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(INDEX_FOR_ZIPCODE);
        bitSet.set(INDEX_FOR_TRANS_AMOUNT);
        bitSet.set(INDEX_FOR_EXPIRY_DATE);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        Mockito.when(request.getZipCode()).thenReturn(FAKE_ZIPCODE);
        Mockito.when(request.getTransactionAmount()).thenReturn(AMOUNT_LS_THAN_100);
        Mockito.when(request.getIncomingTransactionMessage()).thenReturn(new String());
        Mockito.when(request.getExpiryDate()).thenReturn(FAKE_EXPIRY_IN_PAST);
        return request;
    }

    private TransactionAuthRequest fakeRequestWithZipCodeNotPresentAndAmountLsThan200() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(INDEX_FOR_TRANS_AMOUNT);
        bitSet.set(INDEX_FOR_EXPIRY_DATE);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        Mockito.when(request.getTransactionAmount()).thenReturn(AMOUNT_LS_THAN_200);
        Mockito.when(request.getIncomingTransactionMessage()).thenReturn(new String());
        Mockito.when(request.getExpiryDate()).thenReturn(FAKE_EXPIRY_IN_FUTURE);
        return request;
    }

    private TransactionAuthRequest fakeRequestWithZipCodePresentAndAmountGtThan200() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(INDEX_FOR_ZIPCODE);
        bitSet.set(INDEX_FOR_TRANS_AMOUNT);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        Mockito.when(request.getZipCode()).thenReturn(FAKE_ZIPCODE);
        Mockito.when(request.getTransactionAmount()).thenReturn(AMOUNT_GR_THAN_200);
        Mockito.when(request.getIncomingTransactionMessage()).thenReturn(new String());
        return request;
    }

    private TransactionAuthRequest fakeRequestWithZipCodePresentAndAmountLsThan200() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(INDEX_FOR_ZIPCODE);
        bitSet.set(INDEX_FOR_TRANS_AMOUNT);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        Mockito.when(request.getZipCode()).thenReturn(FAKE_ZIPCODE);
        Mockito.when(request.getTransactionAmount()).thenReturn(AMOUNT_LS_THAN_200);
        Mockito.when(request.getExpiryDate()).thenReturn(FAKE_EXPIRY_IN_FUTURE);
        Mockito.when(request.getIncomingTransactionMessage()).thenReturn(new String());
        return request;
    }

    private TransactionAuthRequest fakeRequestWithZipCodeNotPresentAndAmountLsThan100() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(INDEX_FOR_TRANS_AMOUNT);

        TransactionAuthRequest request = Mockito.mock(TransactionAuthRequest.class);
        Mockito.when(request.getDataFields()).thenReturn(bitSet);
        Mockito.when(request.getTransactionAmount()).thenReturn(AMOUNT_LS_THAN_100);
        Mockito.when(request.getExpiryDate()).thenReturn(FAKE_EXPIRY_IN_FUTURE);
        Mockito.when(request.getIncomingTransactionMessage()).thenReturn(new String());

        return request;
    }

}