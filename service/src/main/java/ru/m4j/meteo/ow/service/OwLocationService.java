/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.util.List;

import ru.m4j.meteo.ow.model.LocationDto;

public interface OwLocationService {

    List<LocationDto> requestGeonames();

}
