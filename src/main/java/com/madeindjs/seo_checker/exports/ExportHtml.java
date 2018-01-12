package com.madeindjs.seo_checker.exports;

import com.madeindjs.seo_checker.models.BrokenPage;
import com.madeindjs.seo_checker.models.BrokenPageError;
import com.madeindjs.seo_checker.services.BrokenPages;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportHtml implements Export {

    private BrokenPages pages;

    public ExportHtml(BrokenPages _pages) {
        pages = _pages;
    }

    @Override
    public boolean export(File file) {
        try (FileWriter fw = new FileWriter(file)) {

            fw.write("<!DOCTYPE html><html><head><meta charset=\"utf-8\">");
            fw.write("<style>.error-1{color:red;}.error-2{color:orange;}.error-1{color:blue;}</style>");
            fw.write("</head><body><ul>");

            for (BrokenPage page : pages.getBrokenPages()) {
                String a = String.format("<a href=\"%1$s\">%1$s</a>", page.getUrl());

                fw.write("<li>" + a + "<ul>");

                for (BrokenPageError error : page.getErrors()) {
                    fw.write(String.format(
                            "<li class=\"error-%s\"><input type=\"checkbox\"/>%s</li>",
                            error.getPriority(),
                            error.toString()
                    ));
                }
                fw.write("</ul></li>");
            }

            fw.write("</ul></body></html>");

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
