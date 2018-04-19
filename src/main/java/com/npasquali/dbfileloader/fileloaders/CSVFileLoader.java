package com.npasquali.dbfileloader.fileloaders;

import com.google.inject.internal.util.Lists;
import com.npasquali.dbfileloader.models.CSVFile.CSVFile;
import com.npasquali.dbfileloader.parsers.CSVParser;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Nicola Pasquali
 */
public class CSVFileLoader extends AbstractFileLoader {
    private CSVParser csvParser;
    private List<CSVFile> files;
    private Sql2o sql2o;

    public CSVFileLoader(String basePath, List<String> filesName) {
        logger = Logger.getLogger(CSVFileLoader.class);

        this.basePath = basePath;
        initializeFiles(filesName);
        this.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/test", "test", "test");
        loadDataIntoDatabase();
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

    public void loadDataIntoDatabase() {
        final String sql = "INSERT INTO people(firstname, lastname, age) VALUES (:firstname, :lastname, :age)";

        try (Connection con = sql2o.beginTransaction()) {
            final Query query = con.createQuery(sql);

            files.forEach(file -> {
                file.getRecords().forEach(record -> {
                            for (int i = 0; i < file.getHeaders().size(); i++) {
                                Object value = record.get(i);

                                if (NumberUtils.isDigits(record.get(i))) {
                                    value = Integer.valueOf(record.get(i));
                                } else if (NumberUtils.isNumber(record.get(i))) {
                                    value = Double.valueOf(record.get(i));
                                }

                                query.addParameter(file.getHeaders().get(i), value);
                            }
                            query.addToBatch();
                        }
                );
            });

            query.executeBatch(); // executes entire batch
            con.commit();         // remember to call commit(), else sql2o will automatically rollback.
        }
    }
}
