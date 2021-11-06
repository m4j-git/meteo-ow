/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.mapper.OwMessageDtoModelMapper;
import ru.m4j.meteo.ow.model.OwWeatherDto;

@Service
public class OwDirectoryService {

    private static final Logger log = LoggerFactory.getLogger(OwDirectoryService.class);
    private final ResourceLoader resourceLoader;
    private final OwDao dao;
    private final OwMessageDtoModelMapper mapper;

    public OwDirectoryService(OwMessageDtoModelMapper mapper, ResourceLoader resourceLoader, OwDao dao) {
        this.mapper = mapper;
        this.dao = dao;
        this.resourceLoader = resourceLoader;
    }

    public List<OwWeatherDto> readConditionCodesFromFile() throws IOException {
        final Resource resource = resourceLoader.getResource("classpath:data/ow_condition_codes.csv");
        return readCsvFromFileV2(resource.getInputStream());
    }

    public Set<OwWeather> saveConditionCodesToDb() throws IOException {
        final Set<OwWeather> entityList = mapper.weatherListDtoToWeatherList(readConditionCodesFromFile());
        dao.saveWeatherConditionCodes(entityList);
        log.info("save OpenWeatherMap condition codes to db - ok");
        return entityList;
    }

    public void startUp() throws IOException {
        saveConditionCodesToDb();
    }

    <T> List<T> readCsvFromFileV2(final InputStream stream) throws IOException {
        final CsvMapper mapperCsv = new CsvMapper();
        final CsvSchema schema = mapperCsv.schemaFor(OwWeatherDto.class).withHeader().withColumnReordering(true).withColumnSeparator('\t');
        final ObjectReader reader = mapperCsv.readerFor(OwWeatherDto.class).with(schema);
        return reader.<T>readValues(stream).readAll();
    }

}
