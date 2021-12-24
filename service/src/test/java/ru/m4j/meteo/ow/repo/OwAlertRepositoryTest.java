/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
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

import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;
import ru.m4j.meteo.ow.srv.config.OwTestDaoConfiguration;

@SpringBootTest(classes = OwTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwAlertRepositoryTest extends OwMysqlContainerBase {

    @Autowired
    private OwAlertRepository repo;
    @Autowired
    private OwMessageRepository repoM;

    @BeforeEach
    void setUp() {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

    @Test
    void testCreateAndFindBiId(@Qualifier("message_skinny") OwMessage mes, @Qualifier("alert") OwAlert alertBean) {
        mes = repoM.save(mes);
        mes.addAlert(alertBean);
        final OwAlert ent1 = repo.save(alertBean);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(ent1.getAlertId()).isNotNull();
        final OwAlert ent2 = repo.findById(ent1.getAlertId()).orElseThrow();
        assertThat(ent1).isEqualTo(ent2);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

}
