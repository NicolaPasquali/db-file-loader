package com.npasquali.dbfileloader.models.CSVFile;

import java.util.List;

/**
 * @author Nicola Pasquali
 */
public class CSVFileBuilder {
    private List<String> headers;
    private List<List<String>> records;

    public CSVFileBuilder withHeaders(List<String> headers) {
        this.headers = headers;
        return this;
    }

    public CSVFileBuilder withRecords(List<List<String>> records) {
        this.records = records;
        return this;
    }

    public CSVFile build() {
        return new CSVFile(headers, records);
    }
}
