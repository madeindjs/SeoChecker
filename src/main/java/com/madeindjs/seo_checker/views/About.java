package com.madeindjs.seo_checker.views;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class About extends JFrame {

    private final JPanel rootPanel = new JPanel();

    public About() throws HeadlessException {
        JLabel title = new JLabel("Seo Checker");
        title.setFont(Fonts.h1);
        rootPanel.add(title);

        JLabel desc = new JLabel("https://github.com/madeindjs/SeoChecker");
        desc.setFont(Fonts.p);
        rootPanel.add(desc);

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("About");

        setContentPane(rootPanel);

        setVisible(true);
    }

}
