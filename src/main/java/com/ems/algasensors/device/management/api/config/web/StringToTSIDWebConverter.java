package com.ems.algasensors.device.management.api.config.web;

import io.hypersistence.tsid.TSID;
import org.springframework.core.convert.converter.Converter;

public class StringToTSIDWebConverter implements Converter<String, TSID> {

    /*When receiving a String sensorId in endpoint request, Spring will use this method to convert value to TSID object.
    * This method is registered in WebConfig class for Spring uses it.
    *
    * PS: In recent version of Spring, this configuration is optionally. You don't need to use this class and WebConfig, for
    * Spring converts String to TSID object.
    * */
    @Override
    public TSID convert(String source) {
        return TSID.from(source);
    }

}
