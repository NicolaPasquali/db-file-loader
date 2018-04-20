package com.npasquali.dbfileloader.fileloaders;

import com.google.inject.internal.util.Lists;
import com.npasquali.dbfileloader.models.CSVFile.CSVFile;
import com.npasquali.dbfileloader.parsers.CSVParser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
        try (Connection con = sql2o.beginTransaction()) {
            files.forEach(file -> {
                String sql = createQueryFromFile(file);
                final Query query = con.createQuery(sql);

                file.getRecords().forEach(record -> {
                            for (int index = 0; index < file.getHeaders().size(); index++) {
                                Object value = record.get(index);
                                value = castObject(record, index, value);
                                query.addParameter(file.getHeaders().get(index), value);
                            }
                            query.addToBatch();
                        }
                );
                query.executeBatch();
                con.commit();
            });
        }
    }

    private String createQueryFromFile(CSVFile file) {
        return new StringBuilder("INSERT INTO people (")
                .append(StringUtils.join(file.getHeaders(), ","))
                .append(") VALUES (")
                .append(StringUtils.join(file.getHeaders().stream().map(header -> ":" + header)
                        .collect(Collectors.toList()), ","))
                .append(")")
                .toString();
    }

    private Object castObject(List<String> record, int i, Object value) {
        if (NumberUtils.isDigits(record.get(i))) {
            value = Integer.valueOf(record.get(i));
        } else if (NumberUtils.isNumber(record.get(i))) {
            value = Double.valueOf(record.get(i));
        }
        return value;
    }
}
