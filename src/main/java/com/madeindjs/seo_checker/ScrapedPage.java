package com.madeindjs.seo_checker;

import static com.madeindjs.seo_checker.SeoCrawler.database;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Represent a page crawled by Crawl4J & Scraped with JSoup
 */
public class ScrapedPage {

    private String url;
    /**
     * Content of the first h1 tag found
     */
    private String h1;

    public ScrapedPage(Page page) {
        url = page.getWebURL().getURL();

        String html = null;

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            html = htmlParseData.getHtml();
        } else {
            throw new RuntimeException("Can't parse HTML");
        }

        Document doc = Jsoup.parse(html);

        Element el = doc.selectFirst("h1");
        h1 = el.text();
    }

    public boolean save(Connection connection) throws SQLException {
        String sql = "INSERT INTO pages(url, h1) VALUES(?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, url);
            stmt.setString(2, h1);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SeoCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
