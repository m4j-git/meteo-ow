/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.config;

import com.woodapiary.meteo.ow.aop.OwLoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class OwLoggingAspectConfiguration {

    @Bean
    @Profile("dev")
    public OwLoggingAspect loggingAspect(Environment env) {
        return new OwLoggingAspect(env);
    }
}
