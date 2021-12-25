/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;
import ru.m4j.meteo.ow.srv.config.OwTestDaoConfiguration;

@SpringBootTest(classes = OwTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwFactRepositoryIT extends OwMysqlContainerBase {

    private final Integer geonameId = 1;
    @Autowired
    private OwFactRepository repo;
    @Autowired
    private OwMessageRepository repoM;

    @BeforeEach
    void setUp() {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") OwMessage mes, @Qualifier("fact_skinny") OwFact fact) {
        mes = repoM.save(mes);
        mes.addFact(fact);
        final OwFact ent1 = repo.save(fact);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(ent1.getFactId()).isNotNull();
        final OwFact ent2 = repo.findById(ent1.getFactId()).orElseThrow();
        assertThat(ent1).isEqualTo(ent2);
    }

    @Test
    void testFindFacts(@Qualifier("message") OwMessage mes) {
        OwMessage ent = repoM.save(mes);
        assertThat(repo.count()).isEqualTo(1);
        final List<OwFact> findFacts = repo.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(findFacts.size()).isEqualTo(1);
        assertThat(ent.getFact()).isEqualTo(findFacts.get(0));
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

}
