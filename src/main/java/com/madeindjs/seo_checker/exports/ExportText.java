/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.madeindjs.seo_checker.exports;

import com.madeindjs.seo_checker.services.BrokenPages;
import java.io.File;

/**
 *
 * @author apprenant
 */
public class ExportText implements Export {

    private BrokenPages pages;

    public ExportText(BrokenPages _pages) {
        pages = _pages;
    }

    @Override
    public boolean export(File file) {
        return true;
    }

}
