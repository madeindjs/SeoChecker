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

    public Console() {
        try {
            ResultSet result = selectDuplicateForColumn("h1");
            while (result.next()) {
                String output = String.format(
                        "H1 \"%s\" is duplicates on \"%s\"",
                        result.getString("h1"),
                        result.getString("url")
                );
                System.out.println(output);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
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
                "SELECT count(*) AS count, %s, url FROM pages GROUP BY %s HAVING count > 1",
                column, column
        );
        PreparedStatement stmt = Database.getInstance().prepareStatement(sql);

        return stmt.executeQuery();
    }

    public void display() {

    }

}
