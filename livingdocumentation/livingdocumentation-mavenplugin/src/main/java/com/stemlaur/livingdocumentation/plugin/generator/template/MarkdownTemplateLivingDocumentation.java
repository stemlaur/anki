package com.stemlaur.livingdocumentation.plugin.generator.template;

import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;
import org.apache.maven.plugin.logging.Log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

public class MarkdownTemplateLivingDocumentation implements TemplateLivingDocumentation {

    public MarkdownTemplateLivingDocumentation() {
    }

    public void write(final Log log,
                      final String targetDirectory,
                      final LivingDocumentation livingDocumentation) throws UnsupportedEncodingException, FileNotFoundException {
        log.debug("Creating Markdown template");
        final StringWriter out = new StringWriter();
        final PrintWriter writer = new PrintWriter(out);

        writer.println("# Glossary");
        writer.println("");

        for (LivingDocumentation.Layer layer : livingDocumentation.getLayers()) {
            writer.println("## " + layer.getTitle());
            writer.println("");
            writer.println(layer.getComment());
            writer.println("");
            if (layer.getLink() != null && !layer.getLink().isEmpty()) {
                writer.println("See : " + layer.getLink());
                writer.println("");
            }
            for (LivingDocumentation.Definition definition : layer.getDefinitions()) {
                writer.println("### " + definition.getTitle());
                writer.println("");
                writer.println(definition.getComment());
                writer.println("");
            }
        }

        write("", targetDirectory + "/livingglossary.md", out.toString());
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
