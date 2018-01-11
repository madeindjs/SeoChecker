package com.madeindjs.seo_checker;

import com.madeindjs.seo_checker.controllers.SeoCrawlController;
import com.madeindjs.seo_checker.views.Console;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("You have to specify an website");
            System.exit(0);
        }
        String url = args[0];

        SeoCrawlController crawler = SeoCrawlController.create(url);
        crawler.start();
        new Console();
    }

}
