package com.madeindjs.seo_checker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static final String DATABASE_NAME = "crawler.sqlite";

    public Database() {
        try (Connection connection = getConnection();
                Statement stmt = connection.createStatement();) {

            Class.forName("org.sqlite.JDBC");
            stmt.executeUpdate(ScrapedPage.SCHEMA);
        } catch (ClassNotFoundException e) {
            System.err.println("'org.sqlite.JDBC' was not found");
        } catch (SQLException e) {
            System.err.println("Can't open Database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(String.format("jdbc:sqlite:%s", DATABASE_NAME));
    }

}