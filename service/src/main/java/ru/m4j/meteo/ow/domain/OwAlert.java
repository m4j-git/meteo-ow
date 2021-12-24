/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ow_alert")
public class OwAlert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long alertId;
    /**
     * Name of the alert source
     */
    private String senderName;
    /**
     * Alert event name
     */
    @NotNull
    private String event;
    /**
     * Date and time of the start of the alert, Unix, UTC
     */
    @NotNull
    private OffsetDateTime start;
    /**
     * Date and time of the end of the alert, Unix, UTC
     */
    @NotNull
    @Column(name = "end_")
    private OffsetDateTime end;
    /**
     * Description of the alert
     */
    private String description;

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, updatable = false)
    private OwMessage message;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
