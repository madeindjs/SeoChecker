package com.madeindjs.seo_checker.models;

public enum BrokenPageError {
    TITLE_TOO_SHORT,
    DESCRIPTION_TOO_SHORT,
    DESCRIPTION_TOO_LONG,
    // empty
    TITLE_EMPTY,
    H1_EMPTY,
    DESCRIPTION_EMPTY,
    KEYWORDS_EMPTY,
    // duplicates
    TITLE_DUPLICATE,
    H1_DUPLICATE,
    DESCRIPTION_DUPLICATE,

}
