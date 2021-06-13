/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@SpringBootApplication
@ComponentScan(basePackages = "com.woodapiary.meteo")
public class OwTestApplication {

}
