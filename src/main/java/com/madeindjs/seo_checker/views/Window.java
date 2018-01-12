package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.controllers.SeoCrawlController;
import com.madeindjs.seo_checker.services.Observer;
import com.madeindjs.seo_checker.services.SeoCrawler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Window extends JFrame implements Observer {

    private final JMenuItem aboutMenuItem = new JMenuItem("About");
    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem newMenuItem = new JMenuItem("New scrawl");

    private final JPanel rootPanel = new JPanel();

    private String domain;

    public Window() {
        super();
        SeoCrawler.observers.add(this);

        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Seo Crawler");
        buildMenuBar();
        buildHome();
        this.setContentPane(rootPanel);
        this.setVisible(true);

    }

    public void buildHome() {
        JLabel title = new JLabel("Welcome to Seo Checker");
        title.setFont(Fonts.h1);
        rootPanel.add(title);

        JLabel desc = new JLabel("It will Crawl your website using Crawler4j & check somes SEO rules");
        desc.setFont(Fonts.p);
        rootPanel.add(desc);

        JButton startButton = new JButton("Start my first scrawling");
        startButton.addActionListener(new NewMenuItemListener());
        rootPanel.add(startButton);
    }

    private void buildMenuBar() {
        newMenuItem.addActionListener(new NewMenuItemListener());
        aboutMenuItem.addActionListener(new AboutMenuItemListener());

        fileMenu.add(newMenuItem);
        menuBar.add(fileMenu);

        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
    }

    @Override
    public void onPageCrawled(String url) {
        validate();
        repaint();
    }

    @Override
    public void onCrawlerFinish() {
        // create tree & set it on the content Pane
        ResultTree tree = ResultTree.create(domain);
        setContentPane(new JScrollPane(tree));
        revalidate();
        repaint();
    }

    /**
     * Allow user to run a new
     */
    class NewMenuItemListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JOptionPane inputPane = new JOptionPane();
            domain = inputPane.showInputDialog(
                    null,
                    "Enter an url to scrawl:",
                    "New Scrawler",
                    JOptionPane.QUESTION_MESSAGE
            );

            try {
                new URL(domain);
            } catch (MalformedURLException ex) {
                JOptionPane errorPane = new JOptionPane();
                errorPane.showMessageDialog(null, "Given URL is not valid", "Error append", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                SeoCrawlController crawler;
                crawler = SeoCrawlController.create(domain);
                crawler.startNonBlocking();
            } catch (Exception ex) {
                JOptionPane errorPane = new JOptionPane();
                errorPane.showMessageDialog(null, ex.getMessage(), "Error append", JOptionPane.ERROR_MESSAGE);
                return;
            }

            setContentPane(new JScrollPane(new Loader()));
            validate();
            repaint();
        }

    }

    class AboutMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new About();
        }

    }
}
