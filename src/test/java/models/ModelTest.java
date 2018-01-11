package models;

import com.madeindjs.seo_checker.models.Database;
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

}
