package com.madeindjs.seo_checker.model;

import com.madeindjs.seo_checker.model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jsoup.nodes.Element;

/**
 * Represent an IMG tag without "alt" attribute
 */
public class ImageWithoutAlt {

    public static final String SCHEMA = "DROP TABLE IF EXISTS images_without_alt;"
            + "CREATE TABLE images_without_alt ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "page_id NOT NULL,"
            + "html TEXT NOT NULL );";

    private String html;

    public ImageWithoutAlt(Element element) {
        html = element.toString();
    }

    public boolean save(int pageId) throws SQLException {
        Database database = Database.getInstance();
        String sql = "INSERT INTO images_without_alt(page_id, html) VALUES(?, ?)";
        try (PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setInt(1, pageId);
            stmt.setString(2, html);

            int result = stmt.executeUpdate();
            stmt.close();

            // return false if not inserted
            return result != 0;
        } catch (SQLException ex) {
            throw ex;
        }
    }

}
