package org.highnote.transaction.service;

import org.highnote.transaction.api.TransactionReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class helps to read card transaction messages from a file.
 *
 * @author rimachavan
 */
public class CardTransactionReader implements TransactionReader {

    /**
     * Reads card transaction messages from a given input file.
     *
     * @param fileName from where messages are to be read
     * @return list of string literals
     */
    @Override
    public List<String> read(String fileName) {

        // Validate if input fileName is valid
        if(fileName == null || fileName.isEmpty()) {
            System.out.println("Invalid file name : null");
            return Collections.emptyList();
        }

        List<String> requestMessages = new ArrayList<>();
        Scanner scanner = null;
        File file = null;
        try {
            file = new File(fileName);
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                requestMessages.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            //TODO: add logger
            System.out.println("Invalid file name:" +  e.getMessage());
            System.out.println("You file is located at:" +  file.getAbsolutePath());
        } finally {
            if(scanner != null) {
                scanner.close();
            }
        }
        return requestMessages;
    }
}
