/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwFactRepositoryTest {

    private final Integer geonameId = 1;
    @Autowired
    private OwFactRepository repo;
    @Autowired
    private OwMessageRepository repoM;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertEquals(0, repo.count());
        assertEquals(0, repoM.count());
    }

    @Test
    public void testCreateAndFindById(@Qualifier("message_skinny") OwMessage mes, @Autowired OwFact fact) {
        mes = repoM.save(mes);
        mes.addFact(fact);
        final OwFact ent1 = repo.save(fact);
        assertEquals(1, repo.count());
        assertNotNull(ent1.getFactId());
        final OwFact ent2 = repo.findById(ent1.getFactId()).orElseThrow();
        assertEquals(ent1, ent2);
    }

    @Test
    public void testFindFacts(@Qualifier("message") OwMessage mes) {
        OwMessage ent = repoM.save(mes);
        assertEquals(1, repo.count());
        final List<OwFact> findFacts = repo.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, findFacts.size());
        assertEquals(ent.getFact(), findFacts.get(0));
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertEquals(0, repo.count());
        assertEquals(0, repoM.count());
    }

}
