package com.ems.algasensors.device.management.api.client.impl;

import com.ems.algasensors.device.management.api.client.SensorMonitoringClient;
import io.hypersistence.tsid.TSID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;

    @Value("${temperature-monitoring.base-url}")
    private String baseUrl;

    /*RestClient.Builder already creates a RestClient with Jackson Serializable/Deserializable configuration.*/
    public SensorMonitoringClientImpl(RestClient.Builder restClientBuilder, @Value("${temperature-monitoring.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public void enableMonitoring(TSID sensorId) {
        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

}
