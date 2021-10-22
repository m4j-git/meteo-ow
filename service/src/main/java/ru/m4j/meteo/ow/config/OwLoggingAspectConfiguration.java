/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import ru.m4j.meteo.ow.aop.OwLoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class OwLoggingAspectConfiguration {

    @Bean
    @Profile("dev")
    public OwLoggingAspect loggingAspect(Environment env) {
        return new OwLoggingAspect(env);
    }
}
