/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.repo;

import com.woodapiary.meteo.ow.OwTestApplication;
import com.woodapiary.meteo.ow.domain.OwDaily;
import com.woodapiary.meteo.ow.domain.OwMessage;
import com.woodapiary.meteo.ow.service.OwDirectoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwDailyRepositoryTest {

    @Autowired
    private OwDailyRepository repo;
    @Autowired
    private OwMessageRepository repoM;
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    public void setUp() throws IOException {
        assertThat(repo).isNotNull();
        assertEquals(0, repo.count());
        assertEquals(0, repoM.count());
        dir.saveConditionCodesToDb();
    }

    @Test
    public void testCreateAndFindBiId(@Qualifier("message_skinny") OwMessage mes, @Autowired OwDaily dailyBean) {
        mes = repoM.save(mes);
        mes.addDaily(dailyBean);
        final OwDaily ent1 = repo.save(dailyBean);
        assertEquals(1, repo.count());
        assertNotNull(ent1.getDailyId());
        final OwDaily ent2 = repo.findById(ent1.getDailyId()).orElseThrow();
        assertEquals(ent1, ent2);
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertEquals(0, repo.count());
        assertEquals(0, repoM.count());
    }

}
