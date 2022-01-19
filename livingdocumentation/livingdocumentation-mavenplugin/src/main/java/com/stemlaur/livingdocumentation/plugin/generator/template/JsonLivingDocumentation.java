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
