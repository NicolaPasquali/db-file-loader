package com.npasquali.dbfileloader.fileloaders;

import com.npasquali.dbfileloader.parsers.CSVParser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Nicola Pasquali
 */
public class CSVFileLoader extends AbstractFileLoader {
    private CSVParser csvParser;

    public CSVFileLoader() {
        logger = Logger.getLogger(CSVFileLoader.class);
        csvParser = new CSVParser();
    }

    public void printHeaders(String file) throws FileNotFoundException {
        String pathToFile = assertThatFileExists(file);
        csvParser.getHeader(new File(pathToFile));
    }
}
