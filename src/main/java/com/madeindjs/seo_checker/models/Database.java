package com.madeindjs.seo_checker.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton class for database
 */
public class Database {

    public static final String DATABASE_NAME = "crawler.sqlite";
    private static Database instance;

    private Connection connection;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     *
     * @throws ClassNotFoundException When
     * @throws SQLException When Connection can't be initialized (maybe folder
     * not writteable)
     */
    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            openConnection();
        } catch (ClassNotFoundException ex) {
            System.out.println("Can't find org.sqlite.JDBC");
            System.exit(1);
        } catch (SQLException ex) {
            System.out.println("Connection can't be initialized (maybe folder not writteable)");
            System.exit(1);
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        openConnection();

        return connection.prepareStatement(sql);
    }

    /**
     * Reset Database
     */
    public void reset() throws SQLException {
        closeConnection();
        openConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(ScrapedPage.SCHEMA);
            stmt.executeUpdate(ImageWithoutAlt.SCHEMA);
        } catch (SQLException ex) {
            System.out.println("Can't reset database");
            System.exit(1);
        }
    }

    private void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", DATABASE_NAME));
        }
    }

    public void closeConnection() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

}
