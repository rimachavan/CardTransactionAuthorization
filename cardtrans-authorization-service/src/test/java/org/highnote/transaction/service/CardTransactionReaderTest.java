package org.highnote.transaction.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CardTransactionReaderTest {

    private CardTransactionReader messageReader;
    private static String INVALID_FILENAME = "ABC.XYZ";
    private static String EMPTY_FILENAME = "";
    private static String VALID_FILENAME = "src/test/resources/CardTransTestFile.txt";
    private static String VALID_ENTRY = "0100e016411111111111111112250000001000";

    @Before
    public void init(){
        messageReader = new CardTransactionReader();
    }

    @Test
    public void read_whenFileNameIsNull() {
        List<String> output = messageReader.read(null);
        Assert.assertEquals(output.size(), 0);
    }

    @Test
    public void read_whenFileNameIsEmpty() {
        List<String> output = messageReader.read(EMPTY_FILENAME);
        Assert.assertEquals(output.size(), 0);
    }

    @Test
    public void read_whenFileNameIsInvalid() {
        List<String> output = messageReader.read(INVALID_FILENAME);
        Assert.assertEquals(output.size(), 0);
    }

    @Test
    public void read_whenFileNameIsValid() {
        List<String> output = messageReader.read(VALID_FILENAME);

        Assert.assertEquals(output.size(), 5);
        Assert.assertEquals(output.get(0), VALID_ENTRY);
    }
}