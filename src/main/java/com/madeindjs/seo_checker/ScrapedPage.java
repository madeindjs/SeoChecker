package com.madeindjs.seo_checker;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Represent a page crawled by Crawl4J & Scraped with JSoup
 */
public class ScrapedPage {

    public static final String SCHEMA = "DROP TABLE IF EXISTS pages;"
            + "CREATE TABLE pages ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "url TEXT UNIQUE NOT NULL,"
            + "title TEXT,"
            + "description TEXT,"
            + "keywords TEXT,"
            + "status INTEGER,"
            + "h1 TEXT );";

    private String url;
    /**
     * Content of the first h1 tag found
     */
    private String h1;
    private String title;
    /**
     * Attribute "Content" of the meta tag "description"
     */
    private String description;
    /**
     * Attribute "Content" of the meta tag "keywords"
     */
    private String keywords;
    /**
     * HTTP status code
     */
    private int status;

    public ScrapedPage(Page page) throws ParseException {
        url = page.getWebURL().getURL();
        status = page.getStatusCode();

        // Parse HTML with JSoup
        Document doc = null;
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            doc = Jsoup.parse(html);
        } else {
            throw new ParseException("Can't parse HTML", 0);
        }

        title = doc.title();

        // set h1
        Element eH1 = doc.selectFirst("h1");
        if (eH1 != null) {
            h1 = eH1.text();
        }

        // set description
        Element eDescription = doc.selectFirst("meta[name=\"description\"]");
        if (eDescription != null) {
            description = eDescription.attr("content");
        }

        // set eKeywords
        Element eKeywords = doc.selectFirst("meta[name=\"keywords\"]");
        if (eKeywords != null) {
            keywords = eKeywords.attr("content");
        }

        Element e = doc.selectFirst("meta[name=\"keywords\"]");
        if (e != null) {
            keywords = e.attr("content");
        }

    }

    public boolean save(Connection connection) throws SQLException {
        String sql = "INSERT INTO pages(url, h1, title, description, keywords, status) VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, url);
            stmt.setString(2, h1);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setString(5, keywords);
            stmt.setInt(6, status);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return true;
    }

}
