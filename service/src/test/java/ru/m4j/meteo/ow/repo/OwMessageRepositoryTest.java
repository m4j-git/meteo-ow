/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwMessage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwMessageRepositoryTest {

    private final Integer geonameId = 1;
    @Autowired
    private OwMessageRepository repo;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertEquals(0, repo.count());
    }

    @Test
    public void testCreateAndFindById(@Qualifier("message_skinny") OwMessage mes) {
        OwMessage ent1 = repo.save(mes);
        assertEquals(1, repo.count());
        assertNotNull(ent1.getMessageId());
        assertNotNull(ent1.getCreatedOn());
        final OwMessage ent2 = repo.findById(ent1.getMessageId()).orElseThrow();
        assertEquals(ent1, ent2);
    }

    @Test
    public void testFindLastMessage(@Qualifier("message_skinny") OwMessage mes) {
        OwMessage ent1 = repo.save(mes);
        assertEquals(1, repo.count());
        final OwMessage ent2 = repo.findTopByGeonameIdOrderByCreatedOnDesc(geonameId);
        assertEquals(ent1, ent2);
    }

    @Test
    public void testFindIdByUuid(@Qualifier("message_skinny") OwMessage mes) {
        final OwMessage ent = repo.save(mes);
        assertEquals(1, repo.count());
        assertNotNull(ent.getMessageUuid());
        Long id = repo.findIdByMessageUuid(ent.getMessageUuid());
        assertNotNull(id);
        assertEquals(ent.getMessageId(), id);
    }

    @Test
    public void testFindMessages(@Qualifier("message_skinny") OwMessage mes) {
        OwMessage ent = repo.save(mes);
        assertEquals(1, repo.count());
        final List<OwMessage> findMessages = repo.findMessages(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, findMessages.size());
        assertEquals(ent, findMessages.get(0));
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        assertEquals(0, repo.count());
    }
}
