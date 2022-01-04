/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ow.client.OwRestClientTest.RestClientTestConfig;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.srv.config.OwTestBeanSource;

@RestClientTest(OwRestClientImpl.class)
@AutoConfigureWebClient(registerRestTemplate = true)
@ContextConfiguration(classes = { OwRestClientImpl.class, OwTestBeanSource.class })
@Import(RestClientTestConfig.class)
class OwRestClientTest {

    private final Integer geonameId = 1;

    @Autowired
    private OwRestClientImpl client;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private OwTestBeanSource src;

    @Test
    void whenCallingGetFacts_thenClientMakesCorrectCall() throws IOException {
        OwMessageDto dto = src.readJson();
        List<OwCurrentDto> factList = new ArrayList<>();
        factList.add(dto.getCurrent());
        String json = jacksonMapper.writeValueAsString(factList);

        server.expect(requestTo(client.getUri("messages/facts", geonameId)))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        List<OwCurrentDto> wf = client.getFacts(geonameId, null, null);
        assertThat(wf.size()).isPositive();
    }

    @Test
    void whenCallingGetLastMessage_thenClientMakesCorrectCall() throws IOException {
        OwMessageDto dto = src.readJson();
        String json = jacksonMapper.writeValueAsString(dto);

        server.expect(requestTo(client.getUri("messages/last", geonameId)))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        OwMessageDto wf = client.getLastMessage(geonameId);
        assertThat(wf.getCurrent().getTemp()).isNotNull();
    }

    @Test
    void whenCallingGetMessages_thenClientMakesCorrectCall() throws IOException {
        OwMessageDto dto = src.readJson();
        OwMessageDto[] mesList = new OwMessageDto[] { dto };
        String json = jacksonMapper.writeValueAsString(mesList);
        server.expect(requestTo(client.getUri("messages", geonameId)))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        List<OwMessageDto> wf = client.getMessages(geonameId, null, null);
        assertThat(wf.size()).isPositive();
    }

    @TestConfiguration
    static class RestClientTestConfig {

        @Bean
        public OwRestClientImpl client(RestTemplate restTemplate) {
            return new OwRestClientImpl(restTemplate);
        }
    }

}
