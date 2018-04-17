package com.npasquali.dbfileloader.fileloaders;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * @author Nicola Pasquali
 */
public class CSVFileLoaderTest {
    private CSVFileLoader csvFileLoader;

    @Before
    public void setUp() {
        csvFileLoader = new CSVFileLoader();
        csvFileLoader.setBasePath("test-files");
    }

    @Test
    public void shouldCreatePathCorrectly() {
        Assert.assertEquals("test-files/file.csv", csvFileLoader.buildPathToFile("file.csv"));
    }

    @Test
    public void shouldFindFile() {
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

    @Test
    public void shouldRetrieveHeader() {
        try {
            csvFileLoader.printHeaders("test.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
