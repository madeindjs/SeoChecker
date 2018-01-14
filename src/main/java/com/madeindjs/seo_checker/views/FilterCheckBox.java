package com.madeindjs.seo_checker.views;

import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPagesFilter;
import java.util.Vector;
import javax.swing.JCheckBoxMenuItem;

public class FilterCheckBox extends JCheckBoxMenuItem {

    private static Vector<FilterCheckBox> instances = new Vector();
    private BrokenPageError error;

    public static BrokenPagesFilter getBrokenPagesFilter() {
        BrokenPagesFilter filter = new BrokenPagesFilter();

        for (FilterCheckBox checkbox : instances) {
            if (checkbox.getState()) {
                filter.add(checkbox.getError());
            }
        }

        return filter;
    }

    public static Vector<FilterCheckBox> getInstances() {
        return instances;
    }

    public FilterCheckBox(BrokenPageError _error) {
        super(_error.toString(), true);
        error = _error;
        instances.add(this);
    }

    public BrokenPageError getError() {
        return error;
    }

}
