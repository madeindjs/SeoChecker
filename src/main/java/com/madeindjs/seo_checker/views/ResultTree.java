package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class ResultTree extends JTree {

    public static ResultTree create(String domain) {
        BrokenPages pages;
        try {
            pages = new BrokenPages();
        } catch (SQLException ex) {
            JOptionPane errorPane = new JOptionPane();
            errorPane.showMessageDialog(null, ex.getMessage(), "Error append", JOptionPane.ERROR_MESSAGE);
            return new ResultTree();
        }
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

        return new ResultTree(root);
    }

    private ResultTree(TreeNode root) {
        super(root);
    }

    private ResultTree() {
        super();
    }

}
