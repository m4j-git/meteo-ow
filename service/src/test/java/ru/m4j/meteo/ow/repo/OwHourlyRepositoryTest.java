/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.domain.OwHourly;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.service.OwDirectoryService;

@SpringBootTest
@Transactional
class OwHourlyRepositoryTest {

    @Autowired
    private OwHourlyRepository repo;
    @Autowired
    private OwMessageRepository repoM;
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    public void setUp() throws IOException {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
        dir.saveConditionCodesToDb();
    }

    @Test
    void testCreateAndFindBiId(@Qualifier("message_skinny") OwMessage mes, @Qualifier("hourly") OwHourly hourlyBean) {
        mes = repoM.save(mes);
        hourlyBean.setMessage(mes);
        final OwHourly ent1 = repo.save(hourlyBean);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(ent1.getHourlyId()).isNotNull();
        final OwHourly ent2 = repo.findById(ent1.getHourlyId()).orElseThrow();
        assertThat(ent1).isEqualTo(ent2);
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

}
