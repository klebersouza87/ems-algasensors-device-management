package com.ems.algasensors.device.management.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class RestClientFactory {

    private final String baseUrl;
    private final RestClient.Builder builder;

    public RestClientFactory(RestClient.Builder builder, @Value("${temperature-monitoring.base-url}") String baseUrl) {
        this.builder = builder;
        this.baseUrl = baseUrl;
    }

    /*RestClient.Builder already creates a RestClient with Jackson Serializable/Deserializable configuration.*/
    public RestClient temperatureMonitoringRestClient() {
        return builder
                .baseUrl(baseUrl)
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGatewayException();
                })
                .build();
    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(3));
        return factory;
    }

}
