package models;

import com.madeindjs.seo_checker.models.ImageWithoutAlt;
import java.sql.SQLException;
import java.text.ParseException;
import static junit.framework.TestCase.assertEquals;
import org.jsoup.nodes.Element;

public class ImageWithoutAltTest extends ModelTest {

    public void testSave() throws ParseException, SQLException {
        Element e = new Element("<img />");
        ImageWithoutAlt img = new ImageWithoutAlt(e);
        img.save(1);

        assertEquals(1, countImageWithoutAlt());
    }

}
