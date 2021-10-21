/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ow_message")
@EntityListeners(AuditingEntityListener.class)
public class OwMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private Long messageId;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createdOn;

    @NotNull
    @Column(nullable = false, updatable = false, unique = true, columnDefinition = "varbinary")
    private UUID messageUuid;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Integer geonameId;

    @Setter(value = AccessLevel.NONE)
    @OneToOne(mappedBy = "message", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private OwFact fact;

    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "alertId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<OwAlert> alerts = new ArrayList<>();

    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "dailyId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<OwDaily> dailies = new ArrayList<>();

    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "hourlyId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<OwHourly> hourlies = new ArrayList<>();

    public void addFact(final OwFact fact) {
        this.fact = fact;
        fact.setMessage(this);
    }

    public void addHourlies(final Iterable<OwHourly> items) {
        items.forEach(this::addHourly);
    }

    public OwHourly addHourly(final OwHourly hourly) {
        getHourlies().add(hourly);
        hourly.setMessage(this);
        return hourly;
    }

    public void addDailies(final Iterable<OwDaily> items) {
        items.forEach(this::addDaily);
    }

    public OwDaily addDaily(final OwDaily daily) {
        getDailies().add(daily);
        daily.setMessage(this);
        return daily;
    }

    public void addAlerts(final Iterable<OwAlert> items) {
        items.forEach(this::addAlert);
    }

    public OwAlert addAlert(final OwAlert alert) {
        getAlerts().add(alert);
        alert.setMessage(this);
        return alert;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OwMessage)) {
            return false;
        }
        OwMessage other = (OwMessage) o;
        return messageId != null &&
                messageId.equals(other.getMessageId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messageId);
    }


}
