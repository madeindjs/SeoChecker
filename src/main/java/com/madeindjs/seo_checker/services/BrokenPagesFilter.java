package com.madeindjs.seo_checker.services;

import com.madeindjs.seo_checker.models.BrokenPageError;
import java.util.Vector;

/**
 *
 * @author arousseau
 */
public class BrokenPagesFilter extends Vector<BrokenPageError> {

    /**
     * @param error
     * @return true if filter contins this error
     */
    public boolean hasError(BrokenPageError error) {
        return indexOf(error) != -1;
    }

}
