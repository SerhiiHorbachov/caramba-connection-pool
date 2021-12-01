package com.caramba;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class CarambaDataSourceTest {

    private static final String DB_URL = "jdbc:h2:mem:testdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "";
    private static final String DB_DRIVER = "org.h2.Driver";

    @SneakyThrows
    public static void main(String[] args) {
        DataSource dataSource = new CarambaDataSource(DB_URL, DB_USER, DB_PASS, DB_DRIVER);

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT 1");
                if (resultSet.next()) {
                    long res = resultSet.getLong(1);
                    System.out.println("QUERY EXECUTED");
                }
            }
        }
    }

}