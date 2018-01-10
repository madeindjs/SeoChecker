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

    public Console() throws SQLException {
        for (String column : new String[]{"h1", "title", "description"}) {
            displayEmptyColumn(column);
            displayDuplicatesColumn(column);
        }
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
        String sql = String.format("SELECT url FROM pages WHERE %s IS NULL", column);
        ResultSet result = Database.getInstance()
                .prepareStatement(sql)
                .executeQuery();
        while (result.next()) {
            String output = String.format("%s : %s not set", result.getString("url"), column);
            System.out.println(output);
        }
    }

    public void displayDuplicatesColumn(String column) throws SQLException {
        ResultSet result = selectDuplicateForColumn(column);
        while (result.next()) {
            String output = String.format(
                    "%s : %s \"%s\" duplicates",
                    result.getString("url"),
                    column,
                    result.getString(column)
            );
            System.out.println(output);
        }
    }
}
