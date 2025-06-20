package com.shoppinglist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

public class DataAccessExceptionHandler {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(DataAccessExceptionHandler.class);

    private DataAccessExceptionHandler() {}

    public static void handle(DataAccessException e) {
        LOGGER.error(String.format("Message: %s", e.getMessage()));
        LOGGER.error(String.format("Stacktrace: %s", e.getSuppressed()));
    }
}
