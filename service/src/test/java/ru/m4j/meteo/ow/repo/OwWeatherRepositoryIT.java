/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;
import ru.m4j.meteo.ow.srv.config.OwTestDaoConfiguration;

@SpringBootTest(classes = OwTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwWeatherRepositoryIT extends OwMysqlContainerBase {

    @Autowired
    private OwWeatherRepository repo;

    @BeforeEach
    void setUp() {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
    }

    @Test
    void testCreateAndFindBiId(@Qualifier("weather") OwWeather weatherBean) {
        final OwWeather ent1 = repo.save(weatherBean);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(ent1.getId()).isNotNull();
        final OwWeather ent2 = repo.findById(ent1.getId()).orElseThrow();
        assertThat(ent1).isEqualTo(ent2);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        assertThat(repo.count()).isZero();
    }

}
