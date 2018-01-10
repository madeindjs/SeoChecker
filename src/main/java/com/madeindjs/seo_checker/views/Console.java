package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import java.sql.SQLException;

/**
 * Display result to console
 */
public class Console {

    public Console() throws SQLException {
        BrokenPages pages = new BrokenPages();

        for (BrokenPage page : pages.getBrokenPages()) {
            display(page);
        }
    }

    private void display(BrokenPage page) {
        System.out.println(page.getUrl());

        for (BrokenPageError error : page.getErrors()) {
            System.out.println("\t- " + error);
        }
    }

}
