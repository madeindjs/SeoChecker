package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.services.Observer;
import com.madeindjs.seo_checker.services.SeoCrawler;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Loader extends JPanel implements Observer {

    private JLabel urlScrwaledLabel = new JLabel("Scanning in progress...");

    public Loader() {
        super();
        SeoCrawler.observers.add(this);
        add(urlScrwaledLabel);
    }

    @Override
    public void onPageCrawled(String url) {
        urlScrwaledLabel.setText(url);
        repaint();
    }

    @Override
    public void onCrawlerFinish() {
    }

}
