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
package com.stemlaur.livingdocumentation.plugin.generator;

import com.stemlaur.livingdocumentation.plugin.generator.template.JsonLivingDocumentation;
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
            new JsonLivingDocumentation(this.log).write(targetDirectory, livingDocumentation);
        } catch (Exception ignored) {

        }
    }
}
