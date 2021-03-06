package com.madeindjs.seo_checker.models;

import static com.madeindjs.seo_checker.services.BrokenPages.DESCRIPTION_MAX;
import static com.madeindjs.seo_checker.services.BrokenPages.DESCRIPTION_MIN;
import static com.madeindjs.seo_checker.services.BrokenPages.TITLE_MAX;
import java.awt.Color;
import javax.swing.JCheckBoxMenuItem;

public enum BrokenPageError implements Comparable<BrokenPageError> {

    TITLE_TOO_LONG("Title should not exceed " + TITLE_MAX + " chars", 2),
    DESCRIPTION_TOO_SHORT("Meta tag \"Description\" should not be lower than " + DESCRIPTION_MIN + " chars", 3),
    DESCRIPTION_TOO_LONG("Meta tag \"Description\" should not exceed " + DESCRIPTION_MAX + " chars", 2),
    // empty
    TITLE_EMPTY("\"Title\" tag not found", 1),
    H1_EMPTY("\"h1\" tag not found", 1),
    DESCRIPTION_EMPTY("Meta tag \"Description\" not found", 1),
    KEYWORDS_EMPTY("Meta tag \"Keywords\" not found", 2),
    IMG_ALT_EMPTY("\"alt\" attribute is missing for an \"img\" tag", 1),
    // duplicates
    TITLE_DUPLICATE("\"Title\" is duplicate on another page", 1),
    H1_DUPLICATE("\"h1\" is duplicate on another page", 1),
    DESCRIPTION_DUPLICATE("Meta tag \"Description\" is duplicate on another page", 1),
    // not found
    UNREACHABLE("Page is unreachable", 1);

    private final String description;
    private final int priority;

    BrokenPageError(String _description) {
        description = _description;
        priority = 0;
    }

    BrokenPageError(String _description, int _priority) {
        description = _description;
        priority = _priority;
    }

    @Override
    public String toString() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Color getColor() {
        switch (priority) {
            case 1:
                return new Color(150, 40, 27);
            case 2:
                return new Color(44, 62, 80);
            default:
                return new Color(108, 122, 137);

        }
    }

    public JCheckBoxMenuItem toCheckBox() {
        return new JCheckBoxMenuItem(toString(), true);
    }

}
