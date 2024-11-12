package com.flutter.alloffootball.admin.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorPageConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error-page/400");
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/400");
        ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN, "/error-page/403");
        factory.addErrorPages(errorPage400, errorPage404, errorPage403);
    }
}
