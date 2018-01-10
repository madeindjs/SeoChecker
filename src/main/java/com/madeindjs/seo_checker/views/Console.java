package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.models.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Display result to console
 */
public class Console {

    public static final int DESCRIPTION_MIN = 230;
    public static final int DESCRIPTION_MAX = 320;
    public static final int TITLE_MAX = 320;

    public Console() throws SQLException {
        for (String column : new String[]{"h1", "title", "description"}) {
            displayEmptyColumn(column);
            displayDuplicatesColumn(column);
        }

        displayNotOptimizedTitle();
        displayNotOptimizedDescription();
    }

    /**
     * Create a statement to find duplicate records for a given column
     *
     * @param column
     * @return
     * @throws SQLException
     */
    private ResultSet selectDuplicateForColumn(String column) throws SQLException {
        String sql = String.format(
                "SELECT count(*) AS count, %1$s, url "
                + "FROM pages WHERE %1$s IS NOT NULL "
                + "GROUP BY %1$s HAVING count > 1",
                column
        );
        PreparedStatement stmt = Database.getInstance().prepareStatement(sql);

        return stmt.executeQuery();
    }

    public void displayEmptyColumn(String column) throws SQLException {
        System.out.println(column + " is no set");

        String sql = String.format(
                "SELECT url FROM pages WHERE %1$s IS NULL OR %1$s = ''", column);
        ResultSet result = Database.getInstance()
                .prepareStatement(sql)
                .executeQuery();
        while (result.next()) {
            printUrl(result);
        }
    }

    public void displayDuplicatesColumn(String column) throws SQLException {
        System.out.println(column + " is duplicate");

        ResultSet result = selectDuplicateForColumn(column);
        while (result.next()) {
            printUrl(result);
        }
    }

    /**
     * Title have to be shorter than 71 char
     *
     * @see https://moz.com/blog/googles-longer-snippets
     */
    public void displayNotOptimizedTitle() throws SQLException {
        ResultSet result = Database.getInstance().prepareStatement(
                "SELECT title, url FROM pages"
        ).executeQuery();

        System.out.println(String.format(
                "Title have to be shorter than %s char", TITLE_MAX
        ));

        while (result.next()) {
            String description = result.getString("title");
            int length = description.length();
            // check if length is optimized
            if (length > TITLE_MAX) {
                printUrl(result);
            }
        }
    }

    /**
     * Description have to be between 230 & 320 char
     *
     * @see https://moz.com/blog/googles-longer-snippets
     */
    public void displayNotOptimizedDescription() throws SQLException {
        ResultSet result = Database.getInstance().prepareStatement(
                "SELECT description, url FROM pages"
        ).executeQuery();

        System.out.println(String.format(
                "Description not optimized Description have to be between %s & %s char",
                DESCRIPTION_MIN, DESCRIPTION_MAX
        ));

        while (result.next()) {
            String description = result.getString("description");
            int length = description.length();
            // check if length is optimized
            if (length < DESCRIPTION_MIN || length > DESCRIPTION_MAX) {
                printUrl(result);
            }
        }
    }

    private void printUrl(ResultSet result) {
        try {
            System.out.println("\t- " + result.getString("url"));
        } catch (SQLException ex) {
        }
    }
}
