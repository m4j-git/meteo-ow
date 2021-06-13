/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.requester;

import com.woodapiary.meteo.ow.model.OwMessageDto;

import java.net.URI;

public interface OwMessageClient {

    OwMessageDto request(URI uri);

}
