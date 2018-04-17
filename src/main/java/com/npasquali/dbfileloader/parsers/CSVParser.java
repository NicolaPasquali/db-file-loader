package com.npasquali.dbfileloader.parsers;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Nicola Pasquali
 */
public class CSVParser {
    private Logger logger;

    public CSVParser() {
        logger = Logger.getLogger(CSVParser.class);
    }

    public String getHeader(File file) {
        logger.debug("Getting header of " + file.getName());

        String cvsSeparator = ";";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String header = bufferedReader.readLine();
            if (header != null) {
                logger.debug("Found " + header);
                return header;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
