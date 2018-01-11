package com.madeindjs.seo_checker.models;

import edu.uci.ics.crawler4j.crawler.Page;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represent an asset
 */
public class ScrapedError {

    public static final String SCHEMA = "DROP TABLE IF EXISTS errors;"
            + "CREATE TABLE errors ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "url TEXT UNIQUE NOT NULL,"
            + "status INTEGER);";

    /**
     * SQL id when ScrapedPage is inserted
     */
    private int id;
    private String url;
    private int status;

    public ScrapedError(Page page) {
        url = page.getWebURL().getURL();
        status = page.getStatusCode();
    }

    public ScrapedError(String _url, int _status) {
        url = _url;
        status = _status;
    }

    /**
     * Insert `ScrapedPage` & related `imagesWithoutAlt` in database
     *
     * @return
     * @throws SQLException when SQL error
     */
    public boolean save() throws SQLException {
        String sql = "INSERT INTO errors(url, status) VALUES(?, ?)";
        Database database = Database.getInstance();

        try (PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, url);
            stmt.setInt(2, status);

            int result = stmt.executeUpdate();
            stmt.close();

            // return false if not inserted
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            throw ex;
        }

        setIdFromSql();

        return true;
    }

    private void setIdFromSql() {
        Database database = Database.getInstance();
        // here row was inserted, now we fetch id
        try (PreparedStatement stmt = database.prepareStatement("SELECT id FROM errors WHERE url = ? LIMIT 1")) {
            stmt.setString(1, url);
            ResultSet result = stmt.executeQuery();
            result.next();

            id = result.getInt(1);
            stmt.close();
        } catch (SQLException ex) {
        }
    }

}
