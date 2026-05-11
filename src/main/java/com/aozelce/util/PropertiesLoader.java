package com.aozelce.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * The interface Properties loader.
 *
 * @author Eric Knapp
 */
public interface PropertiesLoader {
    /**
     * The constant LOGGER.
     */
    Logger LOGGER = LogManager.getLogger(PropertiesLoader.class);

    /**
     * Load properties properties.
     *
     * @param propertiesFilePath the properties file path
     * @return the properties
     * @throws Exception the exception
     */
    default Properties loadProperties(String propertiesFilePath) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioException) {
            LOGGER.error("Failed to load properties file: {}", propertiesFilePath, ioException);
            throw ioException;
        } catch (Exception exception) {
            LOGGER.error("Unexpected error loading properties file: {}", propertiesFilePath, exception);
            throw exception;
        }
        return properties;
    }
}