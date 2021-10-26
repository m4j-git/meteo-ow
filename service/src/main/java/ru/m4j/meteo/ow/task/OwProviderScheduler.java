/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.task;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.m4j.meteo.ow.model.GeonameDto;
import ru.m4j.meteo.ow.requester.OwMessageRequester;

@Service
@ConditionalOnProperty(name = "meteo.scheduling.enabled", havingValue = "true")
public class OwProviderScheduler {

    private static final int mFixedRate = 3600 * 4;

    private final OwMessageRequester requester;

    public OwProviderScheduler(OwMessageRequester requester) {
        this.requester = requester;
    }

    @Scheduled(fixedRate = (1000 * mFixedRate) / 10, initialDelay = 3000)
    public void run() {
        List<GeonameDto> gns = requestGeonames();
        for (final GeonameDto gn : gns) {
            requester.requestProvider(gn);
        }
    }

    //FIXME via service
    private List<GeonameDto> requestGeonames() {
        return List.of(new GeonameDto(1, "Moscow", 55.75, 37.6),
                new GeonameDto(2, "Shilovo", 54.571705, 41.083740),
                new GeonameDto(3, "Kaliningrad", 54.710157, 20.510137));
    }

}
