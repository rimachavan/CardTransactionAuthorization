package org.highnote.transaction.api;

import java.util.List;

/**
 * Interface to read incoming messages from a given source.
 *
 * @author rimachavan
 */
public interface TransactionReader {

    /**
     * Method that helps to read transaction from given file.
     *
     * @param fileName file to be read
     * @return listOfMessages
     */
    List<String> read(String fileName);
}
