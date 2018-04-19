package com.npasquali.dbfileloader.parsers;

import com.google.inject.internal.util.Lists;
import com.npasquali.dbfileloader.models.CSVFile.CSVFile;
import com.npasquali.dbfileloader.models.CSVFile.CSVFileBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Nicola Pasquali
 */
public class CSVParser {
    private Logger logger;
    private CSVFileBuilder csvFileBuilder;

    public CSVParser() {
        logger = Logger.getLogger(CSVParser.class);
        csvFileBuilder = new CSVFileBuilder();
    }

    public CSVFile createCSVFile(String path) {
        return createCSVFile(new File(path), ";");
    }

    public CSVFile createCSVFile(File file, String separator) {
        CSVFileBuilder csvFileBuilder = new CSVFileBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            List<List<String>> records = Lists.newArrayList();

            String stringHeaders;
            if ((stringHeaders = bufferedReader.readLine()) != null) {
                csvFileBuilder = csvFileBuilder.withHeaders(getHeader(stringHeaders, separator));
            }

            String stringRecord;
            while ((stringRecord = bufferedReader.readLine()) != null) {
                List<String> record = Lists.newArrayList();
                StringTokenizer tokenizer = new StringTokenizer(stringRecord, separator);

                while (tokenizer.hasMoreElements()) {
                    record.add(tokenizer.nextToken());
                }
                records.add(record);
            }

            return csvFileBuilder
                    .withRecords(records)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getHeader(String stringHeaders, String separator) {
        if (stringHeaders != null) {
            StringTokenizer tokenizer = new StringTokenizer(stringHeaders, separator);
            List<String> headers = Lists.newArrayList();
            while (tokenizer.hasMoreElements()) {
                headers.add(tokenizer.nextToken());
            }
            return headers;
        }
        return null;
    }

//    public List<Map<String, String>> getRecords(File file, String separator, List<String> headers) {
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
//            bufferedReader.skip(1);
//            String record = bufferedReader.readLine();
//            if (record != null) {
//                StringTokenizer tokenizer = new StringTokenizer(record, separator);
//                Map<String, String> recordMap = Maps.newHashMap();
//                while (tokenizer.hasMoreElements()) {
//                    recordMap.put("", tokenizer.nextToken());
//                }
//                return recordMap;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
