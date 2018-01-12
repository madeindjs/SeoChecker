package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Window extends JFrame {

    private JTree tree;
    private String domain;
    private BrokenPages pages;

    public Window(String _domain) {
        domain = _domain;

        try {
            pages = new BrokenPages();
        } catch (SQLException ex) {
            System.exit(1);
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(domain);
        //On invoque la méthode de construction de notre arbre
        buildTree();
        this.setVisible(true);

    }

    private void buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(domain);
        Vector<BrokenPage> pagesSorted = pages.getBrokenPages();
        Collections.sort(pagesSorted);

        for (BrokenPage page : pagesSorted) {
            String href = page.getUrl().replaceFirst(domain, "");
            DefaultMutableTreeNode nodePage = new DefaultMutableTreeNode(href);

            for (BrokenPageError error : page.getErrors()) {
                DefaultMutableTreeNode nodeError = new DefaultMutableTreeNode(error.toString());
                nodePage.add(nodeError);
            }

            root.add(nodePage);
        }

        // create tree & set it on the content Pane
        tree = new JTree(root);
        this.getContentPane().add(new JScrollPane(tree));
    }

}
