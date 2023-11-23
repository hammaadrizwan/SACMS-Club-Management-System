package com.example.sacms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/sacms";//loads the database
    private static final String USERNAME = "root";//credentials
    private static final String PASSWORD = "root";
    //1.2.1 mapping from sequence diagram
    public static Connection getConnection() throws SQLException {//used to make connection with the database
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);// returns the connection to the function call
    }
}
