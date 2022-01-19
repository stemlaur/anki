package com.stemlaur.livingdocumentation.plugin.generator.template;

import com.google.gson.Gson;
import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonLivingDocumentation implements TemplateLivingDocumentation {
    private final Log log;

    public JsonLivingDocumentation(final Log log) {
        this.log = log;
    }

    public void write(final String targetDirectory,
                      final LivingDocumentation livingDocumentation) {
        log.debug("Creating JSON template");
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
