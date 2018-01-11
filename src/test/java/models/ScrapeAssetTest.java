package models;

import com.madeindjs.seo_checker.models.ScrapedError;
import edu.uci.ics.crawler4j.crawler.Page;
import java.sql.SQLException;
import java.text.ParseException;
import static junit.framework.TestCase.assertEquals;

public class ScrapeAssetTest extends ModelTest {

    public void testSave() throws ParseException, SQLException {
        Page page = getPage();
        ScrapedError scrapedAsset = new ScrapedError(page);
        scrapedAsset.save();

        assertEquals(1, countScrapedAsset());
    }

}
