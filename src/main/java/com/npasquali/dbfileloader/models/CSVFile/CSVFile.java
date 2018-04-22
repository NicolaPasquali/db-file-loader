package com.npasquali.dbfileloader.models.CSVFile;

import java.util.List;

/**
 * @author Nicola Pasquali
 */
public class CSVFile {
    private String name;
    private List<String> headers;
    private List<List<String>> records;

    public CSVFile(String name, List<String> headers, List<List<String>> records) {
        this.name = name;
        this.headers = headers;
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getRecords() {
        return records;
    }
}
