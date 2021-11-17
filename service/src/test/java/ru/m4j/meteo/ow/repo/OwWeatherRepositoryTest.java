/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwWeather;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwWeatherRepositoryTest {

    @Autowired
    private OwWeatherRepository repo;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertEquals(0, repo.count());
    }

    @Test
    void testCreateAndFindBiId(@Qualifier("weather") OwWeather weatherBean) {
        final OwWeather ent1 = repo.save(weatherBean);
        assertEquals(1, repo.count());
        assertNotNull(ent1.getId());
        final OwWeather ent2 = repo.findById(ent1.getId()).orElseThrow();
        assertEquals(ent1, ent2);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        assertEquals(0, repo.count());
    }

}
