package com.shoppinglist.util;

import java.sql.SQLException;

public class SQLExceptionHandler {

    public static void handle(SQLException e) {
        System.out.println("SQL Error code: " + e.getErrorCode());
        System.out.println("SQL state: " + e.getSQLState());
        System.out.println("Message: " + e.getMessage());
        e.printStackTrace();
    }
}
