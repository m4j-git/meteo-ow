/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import ru.m4j.meteo.ow.form.OwWeatherFormMapper;
import ru.m4j.meteo.ow.model.LocationDto;
import ru.m4j.meteo.ow.service.LocationService;
import ru.m4j.meteo.ow.service.OwMessageService;
import ru.m4j.meteo.ow.srv.config.OwTestBeanSource;

@ContextConfiguration(classes = { OwController.class, OwWeatherFormMapper.class, OwTestBeanSource.class })
@WebMvcTest(OwController.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwControllerTest {

    private final Integer geonameId = 1;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OwTestBeanSource src;
    @MockBean
    private OwMessageService service;
    @MockBean
    private LocationService locationService;

    @Test
    void showMessagePage_test() throws Exception {
        when(locationService.requestLocations()).thenReturn(List.of(new LocationDto(1, null, null, null)));
        given(service.getLastMessage(geonameId)).willReturn(src.readJson());
        mockMvc.perform(get("/")
            .param("geonameId", "1"))
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().hasNoErrors())
            .andExpect(model().attributeExists("weather"))
            .andExpect(view().name("ow"));
    }

}
