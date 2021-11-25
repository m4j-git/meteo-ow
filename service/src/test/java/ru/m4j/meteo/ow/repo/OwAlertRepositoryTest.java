/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwMessage;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwAlertRepositoryTest {

    @Autowired
    private OwAlertRepository repo;
    @Autowired
    private OwMessageRepository repoM;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertThat(0).isEqualTo(repo.count());
        assertThat(0).isEqualTo(repoM.count());
    }

    @Test
    void testCreateAndFindBiId(@Qualifier("message_skinny") OwMessage mes, @Qualifier("alert") OwAlert alertBean) {
        mes = repoM.save(mes);
        mes.addAlert(alertBean);
        final OwAlert ent1 = repo.save(alertBean);
        assertThat(1).isEqualTo(repo.count());
        assertNotNull(ent1.getAlertId());
        final OwAlert ent2 = repo.findById(ent1.getAlertId()).orElseThrow();
        assertThat(ent1).isEqualTo(ent2);
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertThat(0).isEqualTo(repo.count());
        assertThat(0).isEqualTo(repoM.count());
    }

}
