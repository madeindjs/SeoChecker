package com.madeindjs.seo_checker;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Crawler who fetch website & check some SEO point
 *
 *
 * - use sitemap file
 *
 * - each page are reachable
 *
 * - each page has title
 *
 * - use unique title tags for each page
 *
 * - each page has meta description
 *
 * - ues unique descriptions for each page
 *
 * - url does not just use ID number.
 *
 * - url does not use excessive keywords
 *
 * - url does not have deep nesting of subdirectories
 */
public class SeoCrawler extends WebCrawler {

    /**
     * Database where page will be saved
     */
    public static Database database = new Database();

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();

        return href.startsWith("http://localhost:4000");
    }

    @Override
    public void visit(Page page) {
        try {
            Connection connection = database.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO pages(url) VALUES( ? )");
            stmt.setString(1, page.getWebURL().getURL());

            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SeoCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(String.format("Page %s", page.getWebURL()));
    }

}
