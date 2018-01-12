package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.controllers.SeoCrawlController;
import com.madeindjs.seo_checker.services.BrokenPages;
import com.madeindjs.seo_checker.services.Observer;
import com.madeindjs.seo_checker.services.SeoCrawler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;

public class Window extends JFrame implements Observer {

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem newMenuItem = new JMenuItem("New scrawl");

    private JTree tree;
    private BrokenPages pages;
    private JPanel rootPanel = new JPanel();

    private Loader loader = new Loader();
    private String domain;

    public Window() {
        SeoCrawler.observers.add(this);

        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Seo Crawler");
        buildMenuBar();
        this.setContentPane(rootPanel);
        this.setVisible(true);
    }

    private void buildMenuBar() {
        newMenuItem.addActionListener(new NewMenuItemListener());

        fileMenu.add(newMenuItem);
        menuBar.add(fileMenu);
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
        setContentPane(tree);
        revalidate();
        repaint();
    }

    /**
     * Allow user to run
     */
    class NewMenuItemListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JOptionPane inputPane = new JOptionPane();
            domain = inputPane.showInputDialog(
                    null,
                    "The scrawler will scrape each page founded on this website.",
                    "Enter an url to scrawl",
                    JOptionPane.QUESTION_MESSAGE
            );

            try {
                SeoCrawlController crawler;
                crawler = SeoCrawlController.create(domain);
                crawler.startNonBlocking();
            } catch (Exception ex) {
                JOptionPane errorPane = new JOptionPane();
                errorPane.showMessageDialog(null, ex.getMessage(), "Error append", JOptionPane.ERROR_MESSAGE);
                return;
            }

            setContentPane(loader);
            validate();
            repaint();
        }

    }

}
