package services;

import com.madeindjs.seo_checker.models.ScrapedPage;
import com.madeindjs.seo_checker.services.BrokenPages;
import edu.uci.ics.crawler4j.crawler.Page;
import java.sql.SQLException;
import java.text.ParseException;
import models.ModelTest;

public class BrokenPagesTest extends ModelTest {

    public void testBrokenPagesShouldLoadNothing() throws SQLException {
        BrokenPages pages = new BrokenPages();
        assertEquals(0, pages.getBrokenPages().size());
    }

    public void testBrokenPagesShouldLoad() throws SQLException, ParseException {
        createScrapedPage("<html></html>");

        BrokenPages pages = new BrokenPages();
        assertEquals(1, pages.getBrokenPages().size());
    }

    public void testGetBrokenPage() throws SQLException {
        BrokenPages pages = new BrokenPages();
        assertEquals(0, pages.getBrokenPages().size());
        // should create a new one
        pages.getBrokenPage("http://example.com");
        assertEquals(1, pages.getBrokenPages().size());
        // should not create a new one
        pages.getBrokenPage("http://example.com");
        assertEquals(1, pages.getBrokenPages().size());
    }

    private void createScrapedPage(String html) throws ParseException, SQLException {
        Page page = getPage(html);
        ScrapedPage scrapedPage = new ScrapedPage(page);
        scrapedPage.save();
    }

}
