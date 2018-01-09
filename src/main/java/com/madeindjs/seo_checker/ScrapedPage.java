package com.madeindjs.seo_checker;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;
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
    /**
     * SQL id when ScrapedPage is inserted
     */
    private int id;
    private String url;
    /**
     * Content of the first h1 tag found
     */
    private String h1;
    /**
     * Content of title tag
     */
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
    /**
     * List of images without alt attributes foundeds on this page
     */
    private Vector<ImageWithoutAlt> imagesWithoutAlt = new Vector();

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

        // check if some elements have alt tag
        for (Element eImage : doc.select("img")) {
            if (!eImage.hasAttr("alt")) {
                imagesWithoutAlt.add(new ImageWithoutAlt(eImage));
            }
        }
    }

    /**
     * Insert `ScrapedPage` & related `imagesWithoutAlt` in database
     *
     * @param connection
     * @return
     * @throws SQLException when SQL error
     */
    public boolean save(Connection connection) throws SQLException {
        String sql = "INSERT INTO pages(url, h1, title, description, keywords, status) VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, url);
            stmt.setString(2, h1);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setString(5, keywords);
            stmt.setInt(6, status);

            int result = stmt.executeUpdate();
            stmt.close();

            // return false if not inserted
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            throw ex;
        }

        // here row was inserted, now we fetch id
        try (PreparedStatement stmt = connection.prepareStatement("SELECT id FROM pages WHERE url = ? LIMIT 1")) {
            stmt.setString(1, url);
            ResultSet result = stmt.executeQuery();
            result.next();

            id = result.getInt(1);
            stmt.close();
        } catch (SQLException ex) {
            throw ex;
        }

        // now we insert imagesWithoutAlt
        for (ImageWithoutAlt image : imagesWithoutAlt) {
            image.save(connection, id);
        }

        return true;
    }

}
