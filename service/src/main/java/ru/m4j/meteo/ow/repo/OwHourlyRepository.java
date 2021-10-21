/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import ru.m4j.meteo.ow.domain.OwHourly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwHourlyRepository extends JpaRepository<OwHourly, Long> {

}
