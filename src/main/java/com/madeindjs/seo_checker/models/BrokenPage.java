package com.madeindjs.seo_checker.models;

import java.awt.Color;
import java.util.Vector;

/**
 * A broken page
 */
public class BrokenPage implements Comparable<BrokenPage> {

    private final String url;

    private Vector<BrokenPageError> errors = new Vector();

    public BrokenPage(String _url) {
        url = _url;
    }

    public String getUrl() {
        return url;
    }

    public Vector<BrokenPageError> getErrors() {
        return errors;
    }

    public boolean haveError(BrokenPageError error) {
        return errors.contains(error);
    }

    public void addError(BrokenPageError error) {
        errors.add(error);
    }

    @Override
    public int compareTo(BrokenPage t) {
        return url.compareTo(t.url);
    }

    @Override
    public String toString() {
        return url;
    }

    public Color getColor() {
        BrokenPageError error = errors.firstElement();

        for (BrokenPageError error1 : errors) {
            if (error.getPriority() > error1.getPriority()) {
                error = error1;
            }
        }

        return error.getColor();
    }

}
