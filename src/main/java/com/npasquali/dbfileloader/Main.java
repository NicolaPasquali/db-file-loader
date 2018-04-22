package com.npasquali.dbfileloader;

import com.google.inject.internal.util.Lists;
import com.npasquali.dbfileloader.fileloaders.CSVFileLoader;

import java.util.List;

/**
 * @author Nicola Pasquali
 */
public class Main {
    public static void main(String[] args) {
        List<String> files = Lists.newArrayList();
        files.add("people.csv");

        CSVFileLoader csvFileLoader = new CSVFileLoader("test-files", files);
        csvFileLoader.loadDataIntoDatabase();
    }
}
