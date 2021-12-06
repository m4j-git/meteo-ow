/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.task;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.m4j.meteo.ow.model.LocationDto;
import ru.m4j.meteo.ow.requester.OwMessageRequester;
import ru.m4j.meteo.ow.service.LocationService;

@Service
@ConditionalOnProperty(name = "meteo.scheduling.enabled", havingValue = "true")
public class OwProviderScheduler {

    private static final int FIXED_RATE = 3600 * 2;

    private final OwMessageRequester requester;
    private final LocationService locationService;

    public OwProviderScheduler(OwMessageRequester requester, LocationService locationService) {
        this.requester = requester;
        this.locationService = locationService;
    }

    @Scheduled(fixedRate = (1000 * FIXED_RATE), initialDelay = 3000)
    public void run() {
        List<LocationDto> gns = locationService.requestLocations();
        for (final LocationDto gn : gns) {
            requester.requestProvider(gn);
        }
    }

}
