/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
        builder.addSourceTree(new File("../example/src/main/java"));

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
                                                        "AService",
                                                        "A service.",
                                                        singletonList(LivingDocumentation.Type.DOMAIN_SERVICE)
                                                ),
                                                new Definition(
                                                        "AValueObject",
                                                        "A value object.",
                                                        singletonList(LivingDocumentation.Type.VALUE_OBJECT)
                                                ),
                                                new Definition(
                                                        "AnEntity",
                                                        "An entity.",
                                                        singletonList(LivingDocumentation.Type.ENTITY)
                                                ),
                                                new Definition(
                                                        "AnInnerPackageEvent",
                                                        "An inner domain event.",
                                                        singletonList(LivingDocumentation.Type.DOMAIN_EVENT)
                                                )
                                        ))
                        )
                )
        );
    }
}
