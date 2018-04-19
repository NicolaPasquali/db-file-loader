package com.npasquali.dbfileloader.fileloaders;

import com.google.inject.internal.util.Lists;
import com.npasquali.dbfileloader.models.CSVFile.CSVFile;
import com.npasquali.dbfileloader.parsers.CSVParser;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Nicola Pasquali
 */
public class CSVFileLoader extends AbstractFileLoader {
    private CSVParser csvParser;
    private List<CSVFile> files;

    public CSVFileLoader(String basePath, List<String> filesName) {
        logger = Logger.getLogger(CSVFileLoader.class);

        this.basePath = basePath;
        initializeFiles(filesName);
    }

    private void initializeFiles(List<String> filesName) {
        csvParser = new CSVParser();
        files = Lists.newArrayList();
        filesName.forEach(this::createAndAddCSVFile);
    }

    private void createAndAddCSVFile(String fileName) {
        try {
            String fullPath = assertThatFileExists(fileName);
            files.add(csvParser.createCSVFile(fullPath));
        } catch (FileNotFoundException e) {
            // TODO Find a better way to handle this
            System.out.println("Not found");
        }
    }
}
