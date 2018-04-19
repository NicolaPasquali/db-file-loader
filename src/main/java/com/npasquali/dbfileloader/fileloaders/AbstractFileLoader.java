package com.npasquali.dbfileloader.fileloaders;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Nicola Pasquali
 */
public abstract class AbstractFileLoader {
    protected Logger logger;
    protected FileReader fileReader;
    protected String basePath;

    public String assertThatFileExists(final String fileName) throws FileNotFoundException {
        logger.debug("Checking if " + fileName + " file exists");

        String pathToFile = buildPathToFile(fileName);
        System.out.println(pathToFile);

        final File file = new File(pathToFile);
        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException("Could not find " + fileName + " in " + basePath);
        }
        return pathToFile;
    }

    protected String buildPathToFile(final String fileName) {
        return new StringBuilder()
                .append(this.basePath)
                .append("/")
                .append(fileName)
                .toString();
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
