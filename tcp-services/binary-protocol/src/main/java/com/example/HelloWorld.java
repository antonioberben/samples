package com.example;

import java.sql.*;
import java.util.concurrent.*;

public class HelloWorld {
    public static void main(String[] args) {
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        boolean tlsEnabled = "enabled".equals(System.getenv("TLS"));

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        if (tlsEnabled) {
            url += "?verifyServerCertificate=false&useSSL=true&requireSSL=true";
        }

        System.out.println("Connecting to: " + url);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            Runnable task = () -> {
                try {
                    String sql = "SELECT * FROM demo";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {
                        System.out.println("Ping: " + resultSet.getString("ping"));
                    }
                } catch (SQLException e) {
                    throw new IllegalStateException("Cannot execute the query!", e);
                }
            };

            executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);

            // Keep the application running
            Thread.sleep(Long.MAX_VALUE);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to the database!", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread was interrupted!", e);
        }
    }
}