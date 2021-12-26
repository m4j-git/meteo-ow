/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.form.OwMessageForm;
import ru.m4j.meteo.ow.form.OwWeatherFormMapper;
import ru.m4j.meteo.ow.model.LocationDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.service.LocationService;
import ru.m4j.meteo.ow.service.OwMessageService;

@Slf4j
@Controller
@RequestMapping("/")
//@SessionAttributes("locations")
public class OwController {

    private final OwMessageService service;
    private final LocationService locationService;
    private final OwWeatherFormMapper mapper;

    public OwController(OwMessageService service, LocationService locationService, OwWeatherFormMapper mapper) {
        this.service = service;
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String showMessagePage(Model model, @RequestParam("geonameId") Integer geonameId) {
        log.info("request:" + geonameId);
        OwMessageDto dto = service.getLastMessage(geonameId);
        OwMessageForm form = mapper.mapMessage(dto);
        model.addAttribute("weather", form);
        return "ow";
    }

    @ModelAttribute("locations")
    public LocationDto getLocations() {
        return locationService.requestLocations().get(0);
    }

}
