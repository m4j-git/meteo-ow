/*
  * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.config;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwDaily;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwFeelsLike;
import ru.m4j.meteo.ow.domain.OwHourly;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.domain.OwTemp;
import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.model.LocationDto;

@TestConfiguration
public class OwTestBeanFactory {

    private final Integer geonameId = 1;
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Bean
    @Scope("prototype")
    LocationDto createGeoname() {
        return new LocationDto(geonameId, "Moscow", 55.75, 37.6);
    }

    @Bean(name = "message_skinny")
    @Scope("prototype")
    OwMessage createMessage() {
        return createMessage(null, null, null, null);
    }

    @Bean(name = "message")
    @Scope("prototype")
    OwMessage createMessage(final OwFact fact, final List<OwDaily> daily, final List<OwHourly> hourly, final List<OwAlert> alerts) {
        final OwMessage entity = new OwMessage();
        entity.setMessageUuid(UUID.fromString(messageUuid));
        entity.setGeonameId(geonameId);
        if (fact != null) {
            entity.addFact(fact);
        }
        if (daily != null) {
            daily.forEach(entity::addDaily);
        }
        if (hourly != null) {
            hourly.forEach(entity::addHourly);
        }
        if (alerts != null) {
            alerts.forEach(entity::addAlert);
        }
        return entity;
    }

    @Bean(name = "fact_skinny")
    @Scope("prototype")
    OwFact createFact() {
        return createFact(null);
    }

    @Bean(name = "fact")
    @Scope("prototype")
    OwFact createFact(@Qualifier("weather") final OwWeather weather) {
        final OwFact entity = new OwFact();
        if (weather != null) {
            entity.addWeather(weather);
        }
        entity.setFeelsLike((short) 1480);
        entity.setHumidity((short) 77);
        entity.setDt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602662094L), ZoneId.of("UTC")));
        entity.setPressure((short) 1015);
        entity.setTemp((short) 1570);
        //entity.setWindGust((short) 10);
        entity.setWindDeg((short) 120);
        entity.setWindSpeed((short) 200);
        entity.setClouds((short) 11);
        entity.setDewPoint((short) 1170);
        //entity.setRain1h((short) 10);
        //entity.setSnow1h((short) 20);
        entity.setUvi((short) 140);
        entity.setVisibility((short) 9000);
        entity.setSunrise(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602647986L), ZoneId.of("UTC")));
        entity.setSunset(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602686148L), ZoneId.of("UTC")));
        return entity;
    }

    @Bean
    @Scope("prototype")
    List<OwDaily> createDailyList(OwDaily daily) {
        return List.of(daily);
    }

    @Bean
    @Scope("prototype")
    OwDaily createDaily(@Qualifier("weather2") final OwWeather weather) {
        final OwDaily entity = new OwDaily();
        if (weather != null) {
            entity.addWeather(weather);
        }
        entity.setClouds((short) 14);
        entity.setHumidity((short) 74);
        entity.setDt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602666000L), ZoneId.of("UTC")));
        entity.setPressure((short) 1015);
        //entity.setWindGust((short) 10);
        entity.setWindDeg((short) 135);
        entity.setWindSpeed((short) 240);
        entity.setDewPoint((short) 1160);
        entity.setPop((short) 30);
        entity.setRain1h((short) 10);
        //entity.setVisibility((short) 2);
        entity.setUvi((short) 140);
        OwFeelsLike fl = new OwFeelsLike();
        entity.setFeelsLike(fl);
        fl.setDay((short) 1500);
        fl.setNight((short) 1190);
        fl.setEve((short) 1280);
        fl.setMorn((short) 1120);
        OwTemp temp = new OwTemp();
        entity.setTemp(temp);
        temp.setDay((short) 1620);
        temp.setMin((short) 1050);
        temp.setMax((short) 1741);
        temp.setNight((short) 1400);
        temp.setEve((short) 1410);
        temp.setMorn((short) 1180);
        entity.setSunrise(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602647986L), ZoneId.of("UTC")));
        entity.setSunset(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602686148L), ZoneId.of("UTC")));
        return entity;
    }

    @Bean
    @Scope("prototype")
    List<OwHourly> createHourlyList(@Qualifier("hourly") OwHourly hourly, @Qualifier("hourly2") OwHourly hourly2) {
        return List.of(hourly, hourly2);
    }

    @Bean(name = "hourly")
    @Scope("prototype")
    OwHourly createHourly(@Qualifier("weather") final OwWeather weather) {
        final OwHourly entity = new OwHourly();
        if (weather != null) {
            entity.addWeather(weather);
        }
        entity.setClouds((short) 11);
        entity.setFeelsLike((short) 1470);
        entity.setHumidity((short) 77);
        entity.setDt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602658800L), ZoneId.of("UTC")));
        entity.setPressure((short) 1015);
        entity.setTemp((short) 1570);
        // entity.setWindGust((short) 10);
        entity.setWindDeg((short) 149);
        entity.setWindSpeed((short) 210);
        entity.setDewPoint((short) 1170);
        entity.setPop((short) 0);
        entity.setVisibility((short) 10000);
        return entity;
    }

    @Bean(name = "hourly2")
    @Scope("prototype")
    OwHourly createHourly2(@Qualifier("weather") final OwWeather weather) {
        final OwHourly entity = new OwHourly();
        if (weather != null) {
            entity.addWeather(weather);
        }
        entity.setClouds((short) 13);
        entity.setFeelsLike((short) 1460);
        entity.setHumidity((short) 76);
        entity.setDt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602662400L), ZoneId.of("UTC")));
        entity.setPressure((short) 1015);
        entity.setTemp((short) 1570);
        // entity.setWindGust((short) 10);
        entity.setWindDeg((short) 148);
        entity.setWindSpeed((short) 220);
        entity.setDewPoint((short) 1150);
        entity.setPop((short) 0);
        entity.setVisibility((short) 10000);
        return entity;
    }

    @Bean(name = "weather")
    @Scope("prototype")
    OwWeather createWeather() {
        final OwWeather entity = new OwWeather();
        entity.setId((short) 801);
        entity.setIcon("02d");
        entity.setDescription("few clouds");
        entity.setMain("Clouds");
        return entity;
    }

    @Bean(name = "weather2")
    @Scope("prototype")
    OwWeather createWeather2() {
        final OwWeather entity = new OwWeather();
        entity.setId((short) 500);
        entity.setIcon("10d");
        entity.setDescription("light rain");
        entity.setMain("Rain");
        return entity;
    }

    @Bean
    @Scope("prototype")
    List<OwAlert> createAlertList(OwAlert alert, OwAlert alert2) {
        return List.of(alert, alert2);
    }

    @Bean(name = "alert")
    @Scope("prototype")
    OwAlert createAlert() {
        final OwAlert entity = new OwAlert();
        entity.setStart(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602658800L), ZoneId.systemDefault()));
        entity.setEnd(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602792000L), ZoneId.systemDefault()));
        entity.setEvent("Forestfire");
        entity.setSenderName("");
        entity.setDescription("");
        return entity;
    }

    @Bean(name = "alert2")
    @Scope("prototype")
    OwAlert createAlert2() {
        final OwAlert entity = new OwAlert();
        entity.setStart(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602741600L), ZoneId.systemDefault()));
        entity.setEnd(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1602828000L), ZoneId.systemDefault()));
        entity.setEvent("Wind");
        entity.setSenderName("");
        entity.setDescription("");
        return entity;
    }

}
