/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import ru.m4j.meteo.ow.model.OwMessageDto;

import java.net.URI;

public interface OwMessageClient {

    OwMessageDto request(URI uri);

}
