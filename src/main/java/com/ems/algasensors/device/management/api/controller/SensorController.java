package com.ems.algasensors.device.management.api.controller;

import com.ems.algasensors.device.management.api.model.SensorInput;
import com.ems.algasensors.device.management.api.model.SensorOutput;
import com.ems.algasensors.device.management.common.IdGenerator;
import com.ems.algasensors.device.management.domain.model.Sensor;
import com.ems.algasensors.device.management.domain.model.SensorId;
import com.ems.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;

    @GetMapping
    public Page<SensorOutput> findAll(@PageableDefault Pageable pageable) {
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        return sensors.map(this::convertToSensorOutput);
    }

    @GetMapping("/{sensorId}")
    public SensorOutput find(@PathVariable TSID sensorId) {
        Sensor sensor = getSensor(sensorId);
        return convertToSensorOutput(sensor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput create(@RequestBody SensorInput input) {
        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .name(input.getName())
                .ip(input.getIp())
                .location(input.getLocation())
                .protocol(input.getProtocol())
                .model(input.getModel())
                .enabled(false)
                .build();

        sensor = sensorRepository.saveAndFlush(sensor);

        return convertToSensorOutput(sensor);
    }

    @PutMapping("/{sensorId}")
    public SensorOutput update(@PathVariable TSID sensorId, @Valid @RequestBody SensorInput sensorInput) {
        Sensor sensor = getSensor(sensorId);

        sensor.setIp(sensorInput.getIp());
        sensor.setName(sensorInput.getName());
        sensor.setModel(sensorInput.getModel());
        sensor.setLocation(sensorInput.getLocation());
        sensor.setProtocol(sensorInput.getProtocol());

        sensor = sensorRepository.saveAndFlush(sensor);

        return convertToSensorOutput(sensor);
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableSensor(@PathVariable TSID sensorId) {
        Sensor sensor = getSensor(sensorId);
        sensor.setEnabled(true);
        sensorRepository.save(sensor);
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {
        Sensor sensor = getSensor(sensorId);
        sensorRepository.deleteById(sensor.getId());
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableSensor(@PathVariable TSID sensorId) {
        Sensor sensor = getSensor(sensorId);
        sensor.setEnabled(false);
        sensorRepository.save(sensor);
    }

    private Sensor getSensor(TSID sensorId) {
        return sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private SensorOutput convertToSensorOutput(Sensor sensor) {
        return SensorOutput.builder()
                .id(sensor.getId().getValue())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }

}
