package models;

import com.madeindjs.seo_checker.models.ScrapedPage;
import edu.uci.ics.crawler4j.crawler.Page;
import java.sql.SQLException;
import java.text.ParseException;

public class ScrapedPageTest extends ModelTest {

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

}
