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
package com.stemlaur.anki.application.controllers.livingdoc.generator;

import com.stemlaur.anki.application.controllers.livingdoc.generator.template.JsonLivingDocumentation;
import com.thoughtworks.qdox.JavaProjectBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;

@Slf4j
public class LivingGlossaryGenerator {
    private final String[] sourceDirectories;

    public LivingGlossaryGenerator(String[] sourceDirectories) {
        this.sourceDirectories = sourceDirectories;
        Arrays.sort(sourceDirectories, String.CASE_INSENSITIVE_ORDER);
    }

    public String generateDocumentation() {
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
                    new ParseLivingDocumentation().parseLivingDocumentation(builder);
            return new JsonLivingDocumentation().generate(livingDocumentation);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
