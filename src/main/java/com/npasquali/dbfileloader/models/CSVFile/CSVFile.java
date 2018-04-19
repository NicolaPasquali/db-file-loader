package com.npasquali.dbfileloader.models.CSVFile;

import java.util.List;

/**
 * @author Nicola Pasquali
 */
public class CSVFile {
    private List<String> headers;
    private List<List<String>> records;

    public CSVFile(List<String> headers, List<List<String>> records) {
        this.headers = headers;
        this.records = records;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getRecords() {
        return records;
    }
}
