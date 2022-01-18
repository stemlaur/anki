package com.stemlaur.livingdocumentation.plugin;

import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;
import com.stemlaur.livingdocumentation.plugin.generator.ParseLivingDocumentation;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseLivingDocumentationTest {

    @Test
    public void emptyDocumentation() throws Exception {
        final JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.setEncoding("UTF-8");
        builder.addSourceTree(new File("."));
        System.out.println(new File(".").getAbsolutePath());

        LivingDocumentation livingDocumentation = new ParseLivingDocumentation(new SystemStreamLog())
                .parseLivingDocumentation(builder);
        assertThat(livingDocumentation.getLayers()).isEmpty();
    }
}
