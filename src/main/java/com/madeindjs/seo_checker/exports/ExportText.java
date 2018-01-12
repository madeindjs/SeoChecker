package com.madeindjs.seo_checker.exports;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportText implements Export {

    private BrokenPages pages;

    public ExportText(BrokenPages _pages) {
        pages = _pages;
    }

    @Override
    public boolean export(File file) {
        try (FileWriter fw = new FileWriter(file)) {

            for (BrokenPage page : pages.getBrokenPages()) {
                fw.write(page.getUrl() + "\r\n");

                for (BrokenPageError error : page.getErrors()) {
                    fw.write("\t-" + error.toString() + "\r\n");
                }
            }

            return true;
        } catch (IOException ex) {
            System.out.println("File can't be opened:" + ex.getMessage());
            return false;
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

}
