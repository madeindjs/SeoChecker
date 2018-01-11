package models;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import junit.framework.TestCase;

public class BrokenPageTest extends TestCase {

    public void testHaveError() {
        BrokenPage page = new BrokenPage("http://rousseau-alexandre.fr");
        page.addError(BrokenPageError.H1_EMPTY);

        assertTrue(page.haveError(BrokenPageError.H1_EMPTY));
        assertFalse(page.haveError(BrokenPageError.TITLE_EMPTY));
    }

}
