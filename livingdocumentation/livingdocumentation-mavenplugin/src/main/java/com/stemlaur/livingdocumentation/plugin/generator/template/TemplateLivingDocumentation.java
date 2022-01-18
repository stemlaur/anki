package com.stemlaur.livingdocumentation.plugin.generator.template;

import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;

public interface TemplateLivingDocumentation {
    void write(final String targetDirectory,
               final LivingDocumentation livingDocumentation);
}
