package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import com.madeindjs.seo_checker.services.BrokenPagesFilter;
import java.awt.Component;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;

public class ResultTree extends JTree {

    private BrokenPagesFilter filter;

    public static ResultTree create() {
        return create(null);
    }

    public static ResultTree create(BrokenPagesFilter filter) {
        BrokenPages pages;
        try {
            pages = new BrokenPages(filter);
        } catch (SQLException ex) {
            JOptionPane errorPane = new JOptionPane();
            errorPane.showMessageDialog(null, ex.getMessage(), "Error append", JOptionPane.ERROR_MESSAGE);
            return new ResultTree();
        }
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        Vector<BrokenPage> pagesSorted = pages.getBrokenPages();
        Collections.sort(pagesSorted);

        for (BrokenPage page : pagesSorted) {
            DefaultMutableTreeNode nodePage = new DefaultMutableTreeNode(page.getUrl());

            for (BrokenPageError error : page.getErrors()) {
                DefaultMutableTreeNode nodeError = new DefaultMutableTreeNode(error);
                nodePage.add(nodeError);
            }

            root.add(nodePage);
        }

        return new ResultTree(root);

    }

    private ResultTree(TreeNode root) {
        super(root);
        setRenderer();
    }

    private ResultTree() {
        super();
        setRenderer();
    }

    public void setFilter(BrokenPagesFilter filter) {
        this.filter = filter;
    }

    private void setRenderer() {
        setRootVisible(false);
        TreeCellRenderer renderer = new ErrorCellRenderer();
        setCellRenderer(renderer);
    }

    class ErrorCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;

            if (treeNode.getUserObject() instanceof BrokenPageError) {
                BrokenPageError error = (BrokenPageError) treeNode.getUserObject();
                setForeground(error.getColor());
            }

            return this;
        }

    }

}
