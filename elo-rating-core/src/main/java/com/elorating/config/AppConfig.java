package com.elorating.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by pokor on 10.06.2017.
 */
@Configuration
public class AppConfig {

    /**
     * Bean for handling single page application that always redirect to /
     * @return error page which redirect to /
     */
    @Bean
    public EmbeddedServletContainerCustomizer pageNotFoundHandler() {
        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
    }
}
