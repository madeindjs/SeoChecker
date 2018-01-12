package com.madeindjs.seo_checker.models;

import static com.madeindjs.seo_checker.services.BrokenPages.DESCRIPTION_MAX;
import static com.madeindjs.seo_checker.services.BrokenPages.DESCRIPTION_MIN;
import static com.madeindjs.seo_checker.services.BrokenPages.TITLE_MAX;
import java.awt.Color;

public enum BrokenPageError {

    TITLE_TOO_LONG("Title should not exceed " + TITLE_MAX + " chars", 2),
    DESCRIPTION_TOO_SHORT("Description should not be lower than " + DESCRIPTION_MIN + " chars", 3),
    DESCRIPTION_TOO_LONG("Description should not exceed " + DESCRIPTION_MAX + " chars", 2),
    // empty
    TITLE_EMPTY("<title> tag not found", 1),
    H1_EMPTY("<h1> tag not found", 1),
    DESCRIPTION_EMPTY("<meta name=\"description\"> tag not found", 1),
    KEYWORDS_EMPTY("<meta name=\"keywords\"> tag not found", 2),
    IMG_ALT_EMPTY("'alt' attribute is missing for <img> tag"),
    // duplicates
    TITLE_DUPLICATE("<title> is duplicate on another page", 1),
    H1_DUPLICATE("<h1> is duplicate on another page", 1),
    DESCRIPTION_DUPLICATE("<meta name=\"description\"> is duplicate on another page", 1),
    // not found
    UNREACHABLE("Asset was not found", 1);

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

    public Color getColor() {
        switch (priority) {
            case 1:
                return Color.RED;
            case 2:
                return Color.ORANGE;
            default:
                return Color.blue;

        }
    }

}
