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
package com.stemlaur.anki.application.providers;

import com.stemlaur.anki.application.providers.catalog.SQLDecks;
import com.stemlaur.anki.domain.catalog.spi.Decks;
import com.stemlaur.anki.domain.study.spi.CardProgresses;
import com.stemlaur.anki.domain.study.spi.Sessions;
import com.stemlaur.anki.domain.study.spi.fake.InMemoryCardProgresses;
import com.stemlaur.anki.domain.study.spi.fake.InMemorySessions;
import liquibase.integration.spring.SpringLiquibase;
import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class ProvidersConfiguration {

    @Bean
    Decks sqlDecks(final DSLContext dslContext) {
        return new SQLDecks(dslContext);
    }

    @Bean
    CardProgresses cardProgresses() {
        return new InMemoryCardProgresses();
    }

    @Bean
    Sessions sessions() {
        return new InMemorySessions();
    }

    @Bean
    public SpringLiquibase liquibase(final DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider(final DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DefaultDSLContext dsl(final DefaultConfiguration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Bean
    public DefaultConfiguration configuration(final DataSourceConnectionProvider connectionProvider) {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider);
        return jooqConfiguration;
    }
}
