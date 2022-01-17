package com.stemlaur.livingdocumentation.plugin.generator.template;

import com.google.gson.Gson;
import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;
import org.apache.maven.plugin.logging.Log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class HtmlTemplateLivingDocumentation implements TemplateLivingDocumentation {

    public HtmlTemplateLivingDocumentation() {
    }

    public void write(final Log log,
                      final String targetDirectory,
                      final LivingDocumentation livingDocumentation) throws UnsupportedEncodingException, FileNotFoundException {
        log.debug("Creating HTML template");
        Gson gson = new Gson();

        String extractedContent = gson.toJson(livingDocumentation);

        write("", targetDirectory + "/data.json", extractedContent);
    }

    private static void write(String path, String filename, String content) throws UnsupportedEncodingException,
            FileNotFoundException {
        final String outputFileName = path + filename;
        final String outputEncoding = "UTF-8";
        final FileOutputStream fos = new FileOutputStream(outputFileName);
        final PrintWriter w = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, outputEncoding)));

        w.println(content);
        w.flush();
        w.close();
    }
}
