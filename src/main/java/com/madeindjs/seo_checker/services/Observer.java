package com.madeindjs.seo_checker.services;

public interface Observer {

    public void onPageCrawled(String url);

    public void onCrawlerFinish();

}
