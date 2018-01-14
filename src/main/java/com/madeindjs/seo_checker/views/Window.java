package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.controllers.SeoCrawlController;
import com.madeindjs.seo_checker.exports.Export;
import com.madeindjs.seo_checker.exports.ExportHtml;
import com.madeindjs.seo_checker.exports.ExportText;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import com.madeindjs.seo_checker.services.BrokenPagesFilter;
import com.madeindjs.seo_checker.services.Observer;
import com.madeindjs.seo_checker.services.SeoCrawler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window extends JFrame implements Observer {

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem newMenuItem = new JMenuItem("New scrawl");

    private final JMenu filterMenu = new JMenu("Filter");
    private final FilterCheckBox filterTitleTooLongMenuItem = new FilterCheckBox(BrokenPageError.TITLE_TOO_LONG);
    private final FilterCheckBox filterDescriptionTooShortMenuItem = new FilterCheckBox(BrokenPageError.DESCRIPTION_TOO_SHORT);
    private final FilterCheckBox filterDescriptionTooLongMenuItem = new FilterCheckBox(BrokenPageError.DESCRIPTION_TOO_LONG);
    // empty
    private final FilterCheckBox filterTitleEmpty = new FilterCheckBox(BrokenPageError.TITLE_EMPTY);
    private final FilterCheckBox filterH1Empty = new FilterCheckBox(BrokenPageError.H1_EMPTY);
    private final FilterCheckBox filterDescriptionEmpty = new FilterCheckBox(BrokenPageError.DESCRIPTION_EMPTY);
    private final FilterCheckBox filterKeywordsEmpty = new FilterCheckBox(BrokenPageError.KEYWORDS_EMPTY);
    private final FilterCheckBox filterImageAltEmptyMenuItem = new FilterCheckBox(BrokenPageError.IMG_ALT_EMPTY);
    // duplicates
    private final FilterCheckBox filterTitleDuplicateMenuItem = new FilterCheckBox(BrokenPageError.TITLE_DUPLICATE);
    private final FilterCheckBox filterH1DuplicateMenuItem = new FilterCheckBox(BrokenPageError.H1_DUPLICATE);
    private final FilterCheckBox filterDescriptionDuplicateMenuItem = new FilterCheckBox(BrokenPageError.DESCRIPTION_DUPLICATE);
    private final FilterCheckBox filterUnreachableMenuItem = new FilterCheckBox(BrokenPageError.UNREACHABLE);

    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuItem aboutMenuItem = new JMenuItem("About");
    private final JMenuItem exportMenuItem = new JMenuItem("Export");

    private final JPanel rootPanel = new JPanel();

    protected final JFileChooser fileExporter = new JFileChooser();

    private String domain;

    public Window() {
        super();
        SeoCrawler.observers.add(this);

        buildMenuBar();

        if (BrokenPages.count() > 0) {
            ResultTree tree = ResultTree.create();
            setContentPane(new JScrollPane(tree));
        } else {
            buildHome();
            this.setContentPane(rootPanel);
        }

        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Seo Checker");
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
        exportMenuItem.addActionListener(new ExportMenuItemListener());

        for (FilterCheckBox checkbox : FilterCheckBox.getInstances()) {
            checkbox.addActionListener(new FilterMenuItemListener());
            filterMenu.add(checkbox);
        }

        fileMenu.add(newMenuItem);
        fileMenu.add(exportMenuItem);
        menuBar.add(fileMenu);

        menuBar.add(filterMenu);

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
        repaintTree();
    }

    /**
     * create tree & set it on the content Pane
     */
    private void repaintTree() {
        repaintTree(null);
    }

    private void repaintTree(BrokenPagesFilter filter) {
        ResultTree tree = ResultTree.create(filter);
        setContentPane(new JScrollPane(tree));
        revalidate();
        repaint();

    }

    /**
     * Open a file chooser menu to export a file
     */
    class ExportMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            fileExporter.setSelectedFile(new File("export.txt"));
            fileExporter.setFileFilter(new FileNameExtensionFilter("Text *.txt", "txt"));
            fileExporter.setFileFilter(new FileNameExtensionFilter("HTML *.html", "html"));

            if (fileExporter.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileExporter.getSelectedFile();

                try {
                    Export export;

                    if (file.getAbsolutePath().endsWith("html")) {
                        export = new ExportHtml(new BrokenPages());
                    } else {
                        export = new ExportText(new BrokenPages());
                    }

                    if (export.export(file)) {
                        JOptionPane.showMessageDialog(null,
                                "Your file was exported correctly.",
                                "Success", JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        // else we display a warning
                        JOptionPane.showMessageDialog(null,
                                "Your file can't be export. Check that filename not contains any special character or opened in another application",
                                "Error", JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "can't load error pages",
                            "Error", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    /**
     * Allow user to run a new
     */
    class NewMenuItemListener implements ActionListener {

        @Override
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

    /**
     * Called for each filter. Reload ResultTree
     */
    class FilterMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            BrokenPagesFilter filter = FilterCheckBox.getBrokenPagesFilter();
            repaintTree(filter);
        }

    }
}
