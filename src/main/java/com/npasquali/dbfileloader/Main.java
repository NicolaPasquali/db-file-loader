package com.npasquali.dbfileloader;

import com.npasquali.dbfileloader.fileloaders.CSVFileLoader;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nicola Pasquali
 */
public class Main {
    public static void main(String[] args) {
        new CSVFileLoader("files-to-load", listFiles())
                .loadDataIntoDatabase();
    }

    private static List<String> listFiles() {
        File folder = new File("files-to-load");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        return Arrays.asList(files).stream().map(file -> file.getName()).collect(Collectors.toList());
    }
}
