package org.highnote.transaction.service;

import org.highnote.transaction.api.TransactionAuthorizer;
import org.highnote.transaction.api.TransactionAuthRequest;
import org.highnote.transaction.api.TransactionAuthResponse;
import org.highnote.transaction.card.model.CardTransactionAuthResponse;
import org.highnote.transaction.api.ResponseCode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.highnote.transaction.card.model.CardTransactionMessageConstant.INDEX_FOR_TRANS_AMOUNT;
import static org.highnote.transaction.card.model.CardTransactionMessageConstant.INDEX_FOR_ZIPCODE;

public class CardTransactionAuthorizer implements TransactionAuthorizer {

    @Override
    public TransactionAuthResponse authorize(TransactionAuthRequest request) {

        ResponseCode code = ResponseCode.OK;

        if(request.getDataFields().get(INDEX_FOR_ZIPCODE) && request.getZipCode() > 0 &&
            request.getDataFields().get(INDEX_FOR_TRANS_AMOUNT) && request.getTransactionAmount() >= 20000) {

            /*
             * //When Zip code is provided, a transaction is approved if amount is less than $200
             */
            code = ResponseCode.DE;
        } else if(!request.getDataFields().get(INDEX_FOR_ZIPCODE)
                && request.getDataFields().get(INDEX_FOR_TRANS_AMOUNT)
                && request.getTransactionAmount() > 10000 ){

            /*
             * When Zip code is not provided, a transaction is approved if amount is less than $100
             */
            code = ResponseCode.DE;
        }

        if(getFormattedExpiryDate(request.getExpiryDate()) < getCurrentDate()) {

            /*
             * Expiration Date is greater than the current date
             */
            code = ResponseCode.DE;
        }
        return new CardTransactionAuthResponse.Builder(request, code).build();
    }

    private int getFormattedExpiryDate(int expiryDate) { //1225
        int expiryMonth = expiryDate/100; //12
        int expiryYear = expiryDate - (expiryMonth * 100); //25

        return (expiryYear * 100) + expiryMonth; //2512
    }

    private int getCurrentDate() {
        DateFormat dateformat = new SimpleDateFormat("YYMM");
        return  Integer.parseInt(dateformat.format(new Date()));
    }


}
