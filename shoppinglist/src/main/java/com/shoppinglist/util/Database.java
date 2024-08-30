package com.shoppinglist.util;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // INIT=RUNSCRIPT runs each time a connection is made to the database.
    private static final String H2_DATABASE_URL =
            "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './db_init.sql';DB_CLOSE_DELAY=-1";


    private static final DataSource dataSource;

    private Database () { }

    static {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        // TODO: Change the file name to use a property from application.properties
        jdbcDataSource.setURL(String.format(H2_DATABASE_URL, "./dbFile.sql"));
        dataSource = jdbcDataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static boolean failedBatchExecution(int[] returnValues) {

        for (int returnVal : returnValues) {
            if (returnVal == Statement.EXECUTE_FAILED)
                return true;
        }

        return false;
    }
}