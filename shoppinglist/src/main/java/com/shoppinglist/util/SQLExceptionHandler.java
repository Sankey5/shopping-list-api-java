package com.shoppinglist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class SQLExceptionHandler {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(SQLExceptionHandler.class);

    private SQLExceptionHandler() {}

    public static void handle(SQLException e) {
        LOGGER.error(String.format("SQL Error code: %s", e.getErrorCode()));
        LOGGER.error(String.format("SQL state: %s", e.getSQLState()));
        LOGGER.error(String.format("Message: %s", e.getMessage()));
    }
}
