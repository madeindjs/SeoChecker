package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.services.Observer;
import com.madeindjs.seo_checker.services.SeoCrawler;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Loader extends JPanel implements Observer {

    public Loader() {
        super();
        SeoCrawler.observers.add(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Scanning in progress...");
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
    }

    @Override
    public void onPageCrawled(String url) {
        JLabel urlLabel = new JLabel(url);
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 10);
        urlLabel.setFont(font);
        add(urlLabel, 1);
        repaint();
    }

    @Override
    public void onCrawlerFinish() {
    }

}
