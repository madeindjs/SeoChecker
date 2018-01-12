package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Window extends JFrame {

    private JTree tree;
    private String url;
    private BrokenPages pages;

    public Window(String _url) {
        url = _url;

        try {
            pages = new BrokenPages();
        } catch (SQLException ex) {
            System.exit(1);
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(url);
        //On invoque la méthode de construction de notre arbre
        buildTree();
        this.setVisible(true);

    }

    private void buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(url);

        for (BrokenPage page : pages.getBrokenPages()) {
            DefaultMutableTreeNode nodePage = new DefaultMutableTreeNode(page.getUrl());

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
