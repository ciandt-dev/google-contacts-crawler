package com.ciandt.gcc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReaderPropertiesDomains {

    private static final String PATH_PROPERTIES = "domains.properties";
    private static final String KEY_DOMAINS = "domains";

    public String[] getProperties() throws IOException {

        Properties propertiesDomains = new Properties();
        InputStream readerDomains = this.getClass().getClassLoader()
                .getResourceAsStream(PATH_PROPERTIES);

        try {

            propertiesDomains.load(readerDomains);

            return propertiesDomains.getProperty(KEY_DOMAINS).split(",");


        } finally {

            readerDomains.close();
        }
    }
}
