/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.madeindjs.seo_checker.controllers;

import com.madeindjs.seo_checker.models.Database;
import com.madeindjs.seo_checker.services.SeoCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class SeoCrawlController extends CrawlController {

    /**
     * Folder where temporary files where stored
     */
    private static final String STORAGE_FOLDER = "crawl_data";
    /**
     * Number of crawler at the same times
     */
    private static final int NUMBER_OF_THREAD = 4;
    /**
     * Url to scrawl
     */
    private static final String URL = "http://localhost:4000";

    public static SeoCrawlController create() throws Exception {
        // configure crawler
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(STORAGE_FOLDER);
        config.setThreadShutdownDelaySeconds(1);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // create controller
        SeoCrawlController controller = new SeoCrawlController(config, pageFetcher, robotstxtServer);
        controller.addSeed(URL);

        return controller;
    }

    private SeoCrawlController(CrawlConfig config, PageFetcher pageFetcher, RobotstxtServer robotstxtServer) throws Exception {
        super(config, pageFetcher, robotstxtServer);
    }

    public void start() {
        Database.getInstance().reset();
        start(SeoCrawler.class, NUMBER_OF_THREAD);
    }

}
