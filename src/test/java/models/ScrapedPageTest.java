package models;

import com.madeindjs.seo_checker.models.Database;
import com.madeindjs.seo_checker.models.ScrapedPage;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import junit.framework.TestCase;

/**
 *
 * @author apprenant
 */
public class ScrapedPageTest extends TestCase {

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

    /**
     * Just assert that `getPage` not throw exception
     */
    public void testScrapedPage() throws ParseException {
        Page page = getPage();
        new ScrapedPage(page);
        assertTrue(true);
    }

    public void testSave() throws ParseException, SQLException {
        Page page = getPage();
        ScrapedPage scrapedPage = new ScrapedPage(page);
        scrapedPage.save();

        assertEquals(1, countScrapedPage());
    }

    private Page getPage() {
        return getPage("<html><html>");
    }

    private Page getPage(String html) {
        WebURL url = new WebURL();
        url.setURL("http://example.com");
        Page page = new Page(url);
        HtmlParseData data = new HtmlParseData();
        data.setHtml(html);
        data.setOutgoingUrls(new HashSet());
        page.setParseData(data);

        return page;
    }

    private int countScrapedPage() throws SQLException {
        PreparedStatement stmt = Database.getInstance()
                .prepareStatement("SELECT COUNT(*) FROM pages");
        ResultSet result = stmt.executeQuery();

        return result.getInt(1);
    }

}
