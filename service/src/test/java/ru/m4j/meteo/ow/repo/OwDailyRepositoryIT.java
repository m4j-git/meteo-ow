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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.domain.OwDaily;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.service.OwDirectoryService;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;
import ru.m4j.meteo.ow.srv.config.OwTestDaoConfiguration;

@SpringBootTest(classes = OwTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwDailyRepositoryIT extends OwMysqlContainerBase {

    @Autowired
    private OwDailyRepository repo;
    @Autowired
    private OwMessageRepository repoM;
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    void setUp() throws IOException {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
        dir.saveConditionCodesToDb();
    }

    @Test
    void testCreateAndFindBiId(@Qualifier("message_skinny") OwMessage mes, @Autowired OwDaily dailyBean) {
        mes = repoM.save(mes);
        mes.addDaily(dailyBean);
        final OwDaily ent1 = repo.save(dailyBean);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(ent1.getDailyId()).isNotNull();
        final OwDaily ent2 = repo.findById(ent1.getDailyId()).orElseThrow();
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
