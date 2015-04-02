package com.example.obigrocery.activities;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class DatabaseConnector {
    private final static String DB_URL = "postgres://vqgffudtbdklmq:"
            + "zRxByiySPA45HiFrn8tb4Sg0dS@ec2-184-73-221-47.compute-1.amazonaws.com:5432/d5n2pk53n6pcnk";
    
    public static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException {
        URI dbUri = new URI(DB_URL);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
                + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        System.out.println(dbUrl);

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(dbUrl, username, password);
    }
}
