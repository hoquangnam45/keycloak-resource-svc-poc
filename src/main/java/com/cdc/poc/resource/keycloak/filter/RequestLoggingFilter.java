package com.cdc.poc.resource.keycloak.filter;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class RequestLoggingFilter {
    @Inject
    Router router;

    public void registerFilter(@Observes StartupEvent startupEvent) {
        router.route().order(-10000).handler(rc -> {
            String method = rc.request().method().name();
            String path = rc.request().path();
            String authHeader = rc.request().getHeader("Authorization");

            log.info("HTTP {} {} | Authorization: {}",
                    method,
                    path,
                    authHeader != null ? authHeader : "<none>");

            rc.next();
        });
    }
}