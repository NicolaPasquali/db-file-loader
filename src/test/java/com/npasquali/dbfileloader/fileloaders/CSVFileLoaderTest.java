package com.npasquali.dbfileloader.fileloaders;

import com.google.inject.internal.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Nicola Pasquali
 */
public class CSVFileLoaderTest {
    private CSVFileLoader csvFileLoader;

    @Before
    public void setUp() {
        List<String> files = Lists.newArrayList();
        files.add("test.csv");
        files.add("missing.csv");

        csvFileLoader = new CSVFileLoader("test-files", files);
    }

    @Test
    public void shouldCreatePathCorrectly() {
        Assert.assertEquals("test-files/file.csv", csvFileLoader.buildPathToFile("file.csv"));
    }

    @Test
    public void shouldFindFileAndReturnCorrectPath() {
        try {
            String pathToFile = csvFileLoader.assertThatFileExists("test.csv");
            Assert.assertEquals(csvFileLoader.getBasePath() + "/test.csv", pathToFile);
        } catch (FileNotFoundException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldNotFindFile() {
        try {
            csvFileLoader.assertThatFileExists("missing.csv");
        } catch (FileNotFoundException e) {
            Assert.assertEquals(e.getMessage(), "Could not find missing.csv in " + csvFileLoader.getBasePath());
        }
    }
}
