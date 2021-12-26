/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ow.domain.OwWeather;

@Repository
public interface OwWeatherRepository extends JpaRepository<OwWeather, Short> {

}
