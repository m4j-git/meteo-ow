/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.woodapiary.meteo.*.repo")
@EntityScan(basePackages = {"com.woodapiary.meteo.*.domain"})
@EnableJpaAuditing(/*auditorAwareRef = "springSecurityAuditorAware"*/)
@EnableTransactionManagement
public class OwDatabaseConfiguration {
}
