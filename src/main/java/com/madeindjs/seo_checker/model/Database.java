package com.madeindjs.seo_checker.model;

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
            instance.reset();
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
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", DATABASE_NAME));
        } catch (ClassNotFoundException ex) {
            System.out.println("Can't find org.sqlite.JDBC");
            System.exit(1);
        } catch (SQLException ex) {
            System.out.println("Connection can't be initialized (maybe folder not writteable)");
            System.exit(1);
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private void reset() {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(ScrapedPage.SCHEMA);
            stmt.executeUpdate(ImageWithoutAlt.SCHEMA);
        } catch (SQLException ex) {
            System.out.println("Can't reset database");
            System.exit(1);
        }
    }

}
