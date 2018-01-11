package models;

import com.madeindjs.seo_checker.models.Database;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import junit.framework.TestCase;

public abstract class ModelTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Database.getInstance().reset();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Database.getInstance().closeConnection();
    }

    protected int countImageWithoutAlt() throws SQLException {
        PreparedStatement stmt = Database.getInstance()
                .prepareStatement("SELECT COUNT(*) FROM images_without_alt");
        ResultSet result = stmt.executeQuery();

        return result.getInt(1);
    }

    protected Page getPage() {
        return getPage("<html><html>");
    }

    protected Page getPage(String html) {
        WebURL url = new WebURL();
        url.setURL("http://example.com");
        Page page = new Page(url);
        HtmlParseData data = new HtmlParseData();
        data.setHtml(html);
        data.setOutgoingUrls(new HashSet());
        page.setParseData(data);

        return page;
    }

    protected int countScrapedPage() throws SQLException {
        PreparedStatement stmt = Database.getInstance()
                .prepareStatement("SELECT COUNT(*) FROM pages");
        ResultSet result = stmt.executeQuery();

        return result.getInt(1);
    }

    protected int countScrapedAsset() throws SQLException {
        PreparedStatement stmt = Database.getInstance()
                .prepareStatement("SELECT COUNT(*) FROM assets");
        ResultSet result = stmt.executeQuery();

        return result.getInt(1);
    }

}
