/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ru.m4j.meteo.ow.model.LocationDto;
import ru.m4j.meteo.ow.model.LocationsDto;

@Service
public class OwLocationServiceImpl implements OwLocationService {

    @Override
    public List<LocationDto> requestGeonames() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            URL resource = OwLocationService.class.getClassLoader().getResource("data/locations.xml");
            LocationsDto locations = xmlMapper.readValue(Paths.get(resource.toURI()).toFile(), LocationsDto.class);
            return locations.getLocations();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
