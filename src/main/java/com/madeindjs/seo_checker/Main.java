package com.madeindjs.seo_checker;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Main {

    private static final String STORAGE_FOLDER = "crawl_data";
    private static final int NUMBER_OF_THREAD = 4;
    private static final String URL = "http://localhost:4000";

    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = STORAGE_FOLDER;
        // configure crawler
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // create controller
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(URL);
        controller.start(SeoCrawler.class, NUMBER_OF_THREAD);
    }

}
