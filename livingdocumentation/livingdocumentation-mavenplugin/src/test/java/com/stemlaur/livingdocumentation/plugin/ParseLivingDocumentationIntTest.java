package com.stemlaur.livingdocumentation.plugin;

import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation;
import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation.Definition;
import com.stemlaur.livingdocumentation.plugin.generator.LivingDocumentation.Layer;
import com.stemlaur.livingdocumentation.plugin.generator.ParseLivingDocumentation;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ParseLivingDocumentationIntTest {

    @Test
    public void testParsingDocumentation() {
        final JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.setEncoding("UTF-8");
        builder.addSourceTree(new File("../example/"));

        LivingDocumentation livingDocumentation = new ParseLivingDocumentation(new SystemStreamLog())
                .parseLivingDocumentation(builder);

        assertThat(livingDocumentation).isEqualTo(
                new LivingDocumentation(
                        singletonList(
                                new Layer("Example", "Example domain layer.", "",
                                        Arrays.asList(
                                                new Definition(
                                                        "ADomainEvent",
                                                        "A domain event.",
                                                        singletonList(LivingDocumentation.Type.DOMAIN_EVENT)
                                                ),
                                                new Definition(
                                                        "AnEntity",
                                                        "An entity.",
                                                        singletonList(LivingDocumentation.Type.ENTITY)
                                                ),
                                                new Definition(
                                                        "AService",
                                                        "A service.",
                                                        singletonList(LivingDocumentation.Type.DOMAIN_SERVICE)
                                                ),
                                                new Definition(
                                                        "AValueObject",
                                                        "A value object.",
                                                        singletonList(LivingDocumentation.Type.VALUE_OBJECT)
                                                )
                                        ))
                        )
                )
        );
    }
}
