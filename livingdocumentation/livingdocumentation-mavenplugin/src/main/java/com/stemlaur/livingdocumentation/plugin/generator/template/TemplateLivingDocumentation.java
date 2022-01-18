package com.stemlaur.livingdocumentation.plugin.generator.template;

import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;
import org.apache.maven.plugin.logging.Log;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface TemplateLivingDocumentation {
    void write(final String targetDirectory,
               final LivingDocumentation livingDocumentation) throws UnsupportedEncodingException, FileNotFoundException;
}
