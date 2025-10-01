package com.ems.algasensors.device.management.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SensorInput {
    @NotBlank(message = "name field is mandatory")
    private String name;
    @NotBlank(message = "ip field is mandatory")
    private String ip;
    @NotBlank(message = "location field is mandatory")
    private String location;
    @NotBlank(message = "protocol field is mandatory")
    private String protocol;
    @NotBlank(message = "model field is mandatory")
    private String model;
}
