/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.repo;

import com.woodapiary.meteo.ow.domain.OwAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwAlertRepository extends JpaRepository<OwAlert, Long> {

}
