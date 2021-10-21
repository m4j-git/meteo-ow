/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Configuration
public class OwModelMapperConfig {


    final Converter<Instant, LocalDateTime> instantToLocalDateTime = new AbstractConverter<>() {
        @Override
        protected LocalDateTime convert(final Instant source) {
            if (source == null) {
                return null;
            }
            return LocalDateTime.ofInstant(source, ZoneId.systemDefault());
        }
    };

    final Converter<LocalDateTime, Instant> instantFromLocalDateTime = new AbstractConverter<>() {
        @Override
        protected Instant convert(final LocalDateTime source) {
            if (source == null) {
                return null;
            }
            return source.atZone(ZoneId.systemDefault()).toInstant();
        }
    };

    final Converter<Instant, OffsetDateTime> instantToOffsetDateTime = new AbstractConverter<>() {
        @Override
        protected OffsetDateTime convert(final Instant source) {
            if (source == null) {
                return null;
            }
            return OffsetDateTime.ofInstant(source, ZoneId.of("UTC"));
        }
    };

    final Converter<OffsetDateTime, Instant> instantFromOffsetDateTime = new AbstractConverter<>() {
        @Override
        protected Instant convert(final OffsetDateTime source) {
            if (source == null) {
                return null;
            }
            return source.toInstant();
        }
    };

    private final Converter<Short, Double> doubleScale10FromShort = new AbstractConverter<>() {
        @Override
        protected Double convert(final Short source) {
            if (source == null) {
                return null;
            }
            return (double) (source) / 100;
        }
    };

    private final Converter<Double, Short> doubleScale10ToShort = new AbstractConverter<>() {
        @Override
        protected Short convert(final Double source) {
            if (source == null) {
                return null;
            }
            return (short) (source * 100);
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC)
                .setSkipNullEnabled(true);

        modelMapper.addConverter(instantToOffsetDateTime);
        modelMapper.addConverter(instantFromOffsetDateTime);
        modelMapper.addConverter(instantToLocalDateTime);
        modelMapper.addConverter(instantFromLocalDateTime);

        modelMapper.addConverter(doubleScale10ToShort);
        modelMapper.addConverter(doubleScale10FromShort);
        return modelMapper;
    }

}
