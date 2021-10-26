/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import java.net.URI;

import ru.m4j.meteo.ow.model.OwMessageDto;

public interface OwMessageClient {

    OwMessageDto request(URI uri);

}
