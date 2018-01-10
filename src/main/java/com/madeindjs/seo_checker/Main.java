package com.madeindjs.seo_checker;

import com.madeindjs.seo_checker.controllers.SeoCrawlController;
import com.madeindjs.seo_checker.views.Console;

public class Main {

    public static void main(String[] args) throws Exception {
        SeoCrawlController crawler = SeoCrawlController.create();
        crawler.start();

        new Console();
    }

}
