package com.madeindjs.seo_checker.services;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.models.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Service used to parse database & fetch all broken pages
 */
public class BrokenPages {

    public static final int DESCRIPTION_MIN = 230;
    public static final int DESCRIPTION_MAX = 320;
    public static final int TITLE_MAX = 71;

    private Vector<BrokenPage> brokenPages = new Vector();
    private BrokenPagesFilter filter;

    public BrokenPages() throws SQLException {
        this(null);
    }

    public BrokenPages(BrokenPagesFilter _filter) throws SQLException {
        filter = _filter;
        loadNotOptimizedTitlesPages();
        loadNotOptimizedDescriptionPages();

        // empty
        loadEmpty("h1", BrokenPageError.H1_EMPTY);
        loadEmpty("title", BrokenPageError.TITLE_EMPTY);
        loadEmpty("description", BrokenPageError.DESCRIPTION_EMPTY);
        loadEmpty("keywords", BrokenPageError.KEYWORDS_EMPTY);
        loadImgAltMissing();

        // duplicates
        loadDuplicates("h1", BrokenPageError.H1_DUPLICATE);
        loadDuplicates("title", BrokenPageError.TITLE_DUPLICATE);
        loadDuplicates("description", BrokenPageError.DESCRIPTION_DUPLICATE);

        loadUnreachable();
    }

    public Vector<BrokenPage> getBrokenPages() {
        return brokenPages;
    }

    /**
     * @return Number of broken page
     */
    public static int count() {
        try {
            return (new BrokenPages()).getBrokenPages().size();
        } catch (SQLException ex) {
            return 0;
        }
    }

    /**
     * Description have to be between 230 & 320 char
     *
     * @see https://moz.com/blog/googles-longer-snippets
     * @throws SQLException
     */
    private void loadNotOptimizedDescriptionPages() throws SQLException {
        ResultSet result = Database.getInstance().prepareStatement(
                "SELECT description, url FROM pages"
        ).executeQuery();

        while (result.next()) {
            String url = result.getString("url");
            String description = result.getString("description");

            if (description != null) {
                int length = description.length();
                // check if length is optimized
                if (hasFilter(BrokenPageError.DESCRIPTION_TOO_SHORT) && length < DESCRIPTION_MIN) {
                    getBrokenPage(url).addError(BrokenPageError.DESCRIPTION_TOO_SHORT);
                } else if (hasFilter(BrokenPageError.DESCRIPTION_TOO_LONG) && length > DESCRIPTION_MAX) {
                    getBrokenPage(url).addError(BrokenPageError.DESCRIPTION_TOO_LONG);
                }
            }
        }
    }

    private void loadNotOptimizedTitlesPages() throws SQLException {
        if (!hasFilter(BrokenPageError.TITLE_TOO_LONG)) {
            return;
        }

        ResultSet result = Database.getInstance().prepareStatement(
                "SELECT title, url FROM pages"
        ).executeQuery();

        while (result.next()) {
            String url = result.getString("url");
            String title = result.getString("title");

            if (title != null) {
                int length = title.length();
                // check if length is optimized
                if (length > TITLE_MAX) {
                    getBrokenPage(url).addError(BrokenPageError.TITLE_TOO_LONG);
                }
            }
        }
    }

    private void loadEmpty(String column, BrokenPageError error) throws SQLException {
        if (!hasFilter(error)) {
            return;
        }

        String sql = String.format(
                "SELECT url FROM pages WHERE %1$s IS NULL OR %1$s = ''", column);
        ResultSet result = Database.getInstance()
                .prepareStatement(sql)
                .executeQuery();
        // insert broken pages
        while (result.next()) {
            String url = result.getString("url");
            getBrokenPage(url).addError(error);
        }
    }

    private void loadUnreachable() throws SQLException {
        if (!hasFilter(BrokenPageError.UNREACHABLE)) {
            return;
        }

        ResultSet result = Database.getInstance()
                .prepareStatement("SELECT url FROM errors WHERE status != 200")
                .executeQuery();
        // insert broken pages
        while (result.next()) {
            String url = result.getString("url");
            getBrokenPage(url).addError(BrokenPageError.UNREACHABLE);
        }
    }

    /**
     * Create a statement to find duplicate records for a given column
     *
     * @param column
     * @return
     * @throws SQLException
     */
    private void loadDuplicates(String column, BrokenPageError error) throws SQLException {
        if (!hasFilter(error)) {
            return;
        }

        String sql = String.format(
                "SELECT count(*) AS count, %1$s, url "
                + "FROM pages WHERE %1$s IS NOT NULL "
                + "GROUP BY %1$s HAVING count > 1",
                column
        );
        ResultSet result = Database.getInstance()
                .prepareStatement(sql)
                .executeQuery();
        // insert broken pages
        while (result.next()) {
            String url = result.getString("url");
            getBrokenPage(url).addError(error);
        }
    }

    private void loadImgAltMissing() throws SQLException {
        if (!hasFilter(BrokenPageError.IMG_ALT_EMPTY)) {
            return;
        }

        String sql = String.format(
                "SELECT p.url FROM images_without_alt i INNER JOIN pages p ON i.page_id = p.id");
        ResultSet result = Database.getInstance()
                .prepareStatement(sql)
                .executeQuery();
        // insert broken pages
        while (result.next()) {
            String url = result.getString("url");
            getBrokenPage(url).addError(BrokenPageError.IMG_ALT_EMPTY);
        }
    }

    /**
     * Find broken page by URL or create a new one
     *
     * @param url
     * @return
     */
    public BrokenPage getBrokenPage(String url) {
        // search if page already added
        for (BrokenPage page : brokenPages) {
            if (page.getUrl().equals(url)) {
                return page;
            }
        }
        // create a new one
        BrokenPage page = new BrokenPage(url);
        brokenPages.add(page);

        return page;
    }

    /**
     * Check if filter is set for this error
     *
     * @return
     */
    private boolean hasFilter(BrokenPageError error) {
        if (filter == null) {
            return true;
        } else {
            return filter.hasError(error);
        }
    }

}
