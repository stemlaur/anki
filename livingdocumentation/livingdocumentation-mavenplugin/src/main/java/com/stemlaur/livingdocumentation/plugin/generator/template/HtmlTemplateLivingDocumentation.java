package com.stemlaur.livingdocumentation.plugin.generator.template;

import com.google.gson.Gson;
import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class HtmlTemplateLivingDocumentation implements TemplateLivingDocumentation {
    private final Log log;

    public HtmlTemplateLivingDocumentation(final Log log) {
        this.log = log;
    }

    public void write(final String targetDirectory,
                      final LivingDocumentation livingDocumentation) {
        log.debug("Creating HTML template");
        Gson gson = new Gson();

        String extractedContent = gson.toJson(livingDocumentation);

        writeToFile(targetDirectory + "/data.json", extractedContent);
    }

    private void writeToFile(final String filePath, final String content) {
        log.info("Writing file " + filePath);

        try (PrintWriter out = new PrintWriter(filePath)) {
            out.println(content);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given file " + filePath + " cannot be written at", e);
        }
    }
}
