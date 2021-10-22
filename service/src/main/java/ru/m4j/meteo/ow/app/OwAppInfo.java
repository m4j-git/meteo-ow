/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.app;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.m4j.meteo.ow.OwApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Component
public class OwAppInfo {

    static final Logger log = LoggerFactory.getLogger(OwApplication.class);

    private Environment env;
    private BuildProperties build;

    public OwAppInfo(Environment env, BuildProperties build) {
        this.env = env;
        this.build = build;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void logApplicationStartup() {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store"))
                .map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");

        String contextPath = Optional
                .ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                "\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n\t" +
                        "Git branch: \t{}\n\t" +
                        "Build info: \t{} build {} time {}\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles(),
                env.getProperty("maven.branch-name"),
                build.getVersion(),
                env.getProperty("maven.build-number"),
                build.getTime()
        );
    }

}
