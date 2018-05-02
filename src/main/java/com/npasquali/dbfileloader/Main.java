package com.npasquali.dbfileloader;

import com.npasquali.dbfileloader.fileloaders.CSVFileLoader;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Nicola Pasquali
 */
public class Main {
    public static void main(String[] args) {
        new CSVFileLoader("files-to-load", listFiles())
                .loadDataIntoDatabase();
    }

    // Returns a list containing all the files' name found in the "files-to-load" directory
    private static List<String> listFiles() {
        // Select the folder
        File folder = new File("files-to-load");
        // List all the files in the folder ending with .csv
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        // Create the list of names from the array of files
        return Arrays
                .stream(Objects.requireNonNull(files))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
