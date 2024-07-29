package com.kishan.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.kishan.UrlShortenerConfiguration;
import com.kishan.core.engines.Engine;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GuiceModule extends AbstractModule {

    private ObjectMapper objectMapper;

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public SwaggerBundleConfiguration swaggerBundleConfiguration(UrlShortenerConfiguration configuration) {
        return configuration.getSwaggerBundleConfiguration();
    }

    @Provides
    @Singleton
    public String baseUrl(UrlShortenerConfiguration configuration) {
        return configuration.getBaseUrl();
    }
}
