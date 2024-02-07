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
package com.stemlaur.anki.application.controllers.livingdoc.generator.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stemlaur.anki.application.controllers.livingdoc.generator.LivingDocumentation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;


// <dependency>
//    <groupId>io.github.classgraph</groupId>
//    <artifactId>classgraph</artifactId>
//    <version>X.Y.Z</version>
//</dependency>
@Slf4j
public class JsonLivingDocumentation implements TemplateLivingDocumentation {
    final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public String generate(final LivingDocumentation livingDocumentation) {
        log.debug("Creating JSON template");

        return objectMapper.writeValueAsString(livingDocumentation);
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
