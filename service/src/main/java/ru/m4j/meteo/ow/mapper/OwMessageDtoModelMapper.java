/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.mapper;

import ru.m4j.meteo.ow.domain.*;
import ru.m4j.meteo.ow.model.*;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@Component
public class OwMessageDtoModelMapper {

    private final ModelMapper modelMapper;

    private final Converter<OwPrecipDto, Short> precipToDouble = new AbstractConverter<>() {
        @Override
        protected Short convert(OwPrecipDto source) {
            if (source == null || source.getM1h() == null) {
                return null;
            }
            return (short) (source.getM1h() * 100);
        }
    };
    private final Converter<Short, OwPrecipDto> precipFromDouble = new AbstractConverter<>() {
        @Override
        protected OwPrecipDto convert(Short source) {
            if (source == null) {
                return null;
            }
            final OwPrecipDto res = new OwPrecipDto();
            res.setM1h(((double) source) / 100);
            return res;
        }
    };
    private final Converter<Set<OwWeather>, List<OwWeatherDto>> weatherListDtoFromWeatherList = new AbstractConverter<>() {
        @Override
        protected List<OwWeatherDto> convert(Set<OwWeather> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return weatherListDtoFromWeatherList(source);
        }
    };
    private final Converter<List<OwWeatherDto>, Set<OwWeather>> weatherListDtoToWeatherList = new AbstractConverter<>() {
        @Override
        protected Set<OwWeather> convert(List<OwWeatherDto> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return weatherListDtoToWeatherList(source);
        }
    };
    private final Converter<List<OwDaily>, List<OwDailyDto>> dailyListDtoFromDailyList = new AbstractConverter<>() {
        @Override
        protected List<OwDailyDto> convert(List<OwDaily> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return dailyListDtoFromDailyList(source);
        }
    };
    private final Converter<List<OwDailyDto>, List<OwDaily>> dailyListDtoToDailyList = new AbstractConverter<>() {
        @Override
        protected List<OwDaily> convert(List<OwDailyDto> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return dailyListDtoToDailyList(source);
        }
    };
    private final Converter<List<OwHourly>, List<OwHourlyDto>> hourlyListDtoFromHourlyList = new AbstractConverter<>() {
        @Override
        protected List<OwHourlyDto> convert(List<OwHourly> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return hourlyListDtoFromHourlyList(source);
        }
    };
    private final Converter<List<OwHourlyDto>, List<OwHourly>> hourlyListDtoToHourlyList = new AbstractConverter<>() {
        @Override
        protected List<OwHourly> convert(List<OwHourlyDto> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return hourlyListDtoToHourlyList(source);
        }
    };
    private final Converter<List<OwAlert>, List<OwAlertDto>> alertListDtoFromAlertList = new AbstractConverter<>() {
        @Override
        protected List<OwAlertDto> convert(List<OwAlert> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return alertListDtoFromAlertList(source);
        }
    };
    private final Converter<List<OwAlertDto>, List<OwAlert>> alertListDtoToAlertList = new AbstractConverter<>() {
        @Override
        protected List<OwAlert> convert(List<OwAlertDto> source) {
            if (source == null || source.size() == 0) {
                return null;
            }
            return alertListDtoToAlertList(source);
        }
    };


    public OwMessageDtoModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        modelMapper.createTypeMap(OwMessageDto.class, OwMessage.class).addMapping(OwMessageDto::getCurrent, OwMessage::addFact)
                .addMappings(mapper -> mapper.using(alertListDtoToAlertList).map(OwMessageDto::getAlerts, OwMessage::addAlerts))
                .addMappings(mapper -> mapper.using(dailyListDtoToDailyList).map(OwMessageDto::getDaily, OwMessage::addDailies))
                .addMappings(mapper -> mapper.using(hourlyListDtoToHourlyList).map(OwMessageDto::getHourly, OwMessage::addHourlies));

        modelMapper.createTypeMap(OwMessage.class, OwMessageDto.class)
                .addMapping(OwMessage::getFact, OwMessageDto::setCurrent)
                .addMappings(mapper -> mapper.using(alertListDtoFromAlertList).map(OwMessage::getAlerts, OwMessageDto::setAlerts))
                .addMappings(mapper -> mapper.using(dailyListDtoFromDailyList).map(OwMessage::getDailies, OwMessageDto::setDaily))
                .addMappings(mapper -> mapper.using(hourlyListDtoFromHourlyList).map(OwMessage::getHourlies, OwMessageDto::setHourly));

        modelMapper.createTypeMap(OwCurrentDto.class, OwFact.class)
                .addMappings(mapper -> mapper.using(precipToDouble).map(OwCurrentDto::getRain, OwFact::setRain1h))
                .addMappings(mapper -> mapper.using(precipToDouble).map(OwCurrentDto::getSnow, OwFact::setSnow1h))
                .addMappings(mapper -> mapper.using(weatherListDtoToWeatherList).map(OwCurrentDto::getWeather, OwFact::setWeather));

        modelMapper.createTypeMap(OwFact.class, OwCurrentDto.class)
                .addMappings(mapper -> mapper.using(precipFromDouble).map(OwFact::getRain1h, OwCurrentDto::setRain))
                .addMappings(mapper -> mapper.using(precipFromDouble).map(OwFact::getSnow1h, OwCurrentDto::setSnow))
                .addMappings(mapper -> mapper.using(weatherListDtoFromWeatherList).map(OwFact::getWeather, OwCurrentDto::setWeather));

        modelMapper.createTypeMap(OwDailyDto.class, OwDaily.class)
                .addMappings(mapper -> mapper.using(weatherListDtoToWeatherList).map(OwDailyDto::getWeather, OwDaily::setWeather));

        modelMapper.createTypeMap(OwDaily.class, OwDailyDto.class)
                .addMappings(mapper -> mapper.using(weatherListDtoFromWeatherList).map(OwDaily::getWeather, OwDailyDto::setWeather));

        modelMapper.createTypeMap(OwHourlyDto.class, OwHourly.class)
                .addMappings(mapper -> mapper.using(precipToDouble).map(OwHourlyDto::getRain, OwHourly::setRain1h))
                .addMappings(mapper -> mapper.using(precipToDouble).map(OwHourlyDto::getSnow, OwHourly::setSnow1h))
                .addMappings(mapper -> mapper.using(weatherListDtoToWeatherList).map(OwHourlyDto::getWeather, OwHourly::setWeather));

        modelMapper.createTypeMap(OwHourly.class, OwHourlyDto.class)
                .addMappings(mapper -> mapper.using(precipFromDouble).map(OwHourly::getRain1h, OwHourlyDto::setRain))
                .addMappings(mapper -> mapper.using(precipFromDouble).map(OwHourly::getSnow1h, OwHourlyDto::setSnow))
                .addMappings(mapper -> mapper.using(weatherListDtoFromWeatherList).map(OwHourly::getWeather, OwHourlyDto::setWeather));
    }

    public OwMessage messageDtoToMessage(final OwMessageDto dto) {
        return modelMapper.map(dto, OwMessage.class);
    }

    public OwMessageDto messageDtoFromMessage(final OwMessage entity) {
        return modelMapper.map(entity, OwMessageDto.class);
    }

    public OwFact factDtoToFact(final OwCurrentDto dto) {
        return modelMapper.map(dto, OwFact.class);
    }

    public OwCurrentDto factDtoFromFact(final OwFact entity) {
        return modelMapper.map(entity, OwCurrentDto.class);
    }

    public Set<OwWeather> weatherListDtoToWeatherList(final List<OwWeatherDto> dtoList) {
        final Type listType = new TypeToken<Set<OwWeather>>() {
        }.getType();
        return modelMapper.map(dtoList, listType);
    }

    public List<OwWeatherDto> weatherListDtoFromWeatherList(final Set<OwWeather> entityList) {
        final Type listType = new TypeToken<List<OwWeatherDto>>() {
        }.getType();
        return modelMapper.map(entityList, listType);
    }

    public List<OwAlert> alertListDtoToAlertList(final List<OwAlertDto> dtoList) {
        final Type listType = new TypeToken<List<OwAlert>>() {
        }.getType();
        return modelMapper.map(dtoList, listType);
    }

    public List<OwAlertDto> alertListDtoFromAlertList(final List<OwAlert> entityList) {
        final Type listType = new TypeToken<List<OwAlertDto>>() {
        }.getType();
        return modelMapper.map(entityList, listType);
    }

    public List<OwDaily> dailyListDtoToDailyList(final List<OwDailyDto> dtoList) {
        final Type listType = new TypeToken<List<OwDaily>>() {
        }.getType();
        return modelMapper.map(dtoList, listType);
    }

    public List<OwDailyDto> dailyListDtoFromDailyList(final List<OwDaily> entityList) {
        final Type listType = new TypeToken<List<OwDailyDto>>() {
        }.getType();
        List<OwDailyDto> res = modelMapper.map(entityList, listType);
        //res.sort(new OwDailyComparator());
        return res;
    }

    public List<OwHourly> hourlyListDtoToHourlyList(final List<OwHourlyDto> dtoList) {
        final Type listType = new TypeToken<List<OwHourly>>() {
        }.getType();
        return modelMapper.map(dtoList, listType);
    }

    public List<OwHourlyDto> hourlyListDtoFromHourlyList(final List<OwHourly> entityList) {
        final Type listType = new TypeToken<List<OwHourlyDto>>() {
        }.getType();
        List<OwHourlyDto> res = modelMapper.map(entityList, listType);
        //res.sort(new OwHourlyComparator());
        return res;
    }

    public List<OwMessageDto> messagesDtoFromMessages(final List<OwMessage> entityList) {
        final Type listType = new TypeToken<List<OwMessageDto>>() {
        }.getType();
        return modelMapper.map(entityList, listType);
    }

    public List<OwCurrentDto> factsDtoFromFacts(final List<OwFact> entityList) {
        final Type listType = new TypeToken<List<OwCurrentDto>>() {
        }.getType();
        return modelMapper.map(entityList, listType);
    }


}
