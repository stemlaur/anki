package com.stemlaur.livingdocumentation.plugin.generator;

import com.stemlaur.livingdocumentation.plugin.generator.template.HtmlTemplateLivingDocumentation;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.Arrays;

public class LivingGlossaryGenerator {
    private final Log log;
    private final String targetDirectory;
    private final String[] sourceDirectories;

    public LivingGlossaryGenerator(final Log log, String targetDirectory, String[] sourceDirectories) {
        this.log = log;
        this.targetDirectory = targetDirectory;
        this.sourceDirectories = sourceDirectories;
        Arrays.sort(sourceDirectories, String.CASE_INSENSITIVE_ORDER);
    }

    public void generateDocumentation() {
        try {

            final JavaProjectBuilder builder = new JavaProjectBuilder();
            builder.setEncoding("UTF-8");

            // Adding all .java files in a source tree (recursively).
            if (sourceDirectories != null) {
                for (final String sourceDirectory : sourceDirectories) {
                    File file = new File(sourceDirectory);
                    if (file.exists()) {
                        log.info(file.getAbsolutePath() + " added to the source tree !");
                        builder.addSourceTree(file);
                    } else {
                        log.warn(file.getAbsolutePath() + " does not exist");
                    }
                }
            }

            final LivingDocumentation livingDocumentation =
                    new ParseLivingDocumentation(this.log).parseLivingDocumentation(builder);
            new HtmlTemplateLivingDocumentation().write(this.log, targetDirectory, livingDocumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
