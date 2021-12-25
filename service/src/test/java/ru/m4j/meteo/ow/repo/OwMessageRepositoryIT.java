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

import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;
import ru.m4j.meteo.ow.srv.config.OwTestDaoConfiguration;

@SpringBootTest(classes = OwTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwMessageRepositoryIT extends OwMysqlContainerBase {

    private final Integer geonameId = 1;
    @Autowired
    private OwMessageRepository repo;

    @BeforeEach
    void setUp() {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") OwMessage mes) {
        OwMessage ent1 = repo.save(mes);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(ent1.getMessageId()).isNotNull();
        assertThat(ent1.getCreatedOn()).isNotNull();
        final OwMessage ent2 = repo.findById(ent1.getMessageId()).orElseThrow();
        assertThat(ent1).isEqualTo(ent2);
    }

    @Test
    void testFindIdByUuid(@Qualifier("message_skinny") OwMessage mes) {
        final OwMessage ent = repo.save(mes);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(ent.getMessageUuid()).isNotNull();
        Long id = repo.findIdByMessageUuid(ent.getMessageUuid());
        assertThat(id).isNotNull();
        assertThat(ent.getMessageId()).isEqualTo(id);
    }

    @Test
    void testFindMessages(@Qualifier("message_skinny") OwMessage mes) {
        OwMessage ent = repo.save(mes);
        assertThat(repo.count()).isEqualTo(1);
        final List<OwMessage> findMessages = repo.findMessages(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(findMessages.size()).isEqualTo(1);
        assertThat(ent).isEqualTo(findMessages.get(0));
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        assertThat(repo.count()).isZero();
    }
}
