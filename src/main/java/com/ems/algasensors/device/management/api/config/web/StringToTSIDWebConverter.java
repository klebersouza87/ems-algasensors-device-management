package com.ems.algasensors.device.management.api.config.web;

import io.hypersistence.tsid.TSID;
import org.springframework.core.convert.converter.Converter;

public class StringToTSIDWebConverter implements Converter<String, TSID> {

    /*When receiving a String sensorId in endpoint request, Spring will use this method to convert value to TSID object.
    * This method is registered in WebConfig class for Spring uses it.
    * */
    @Override
    public TSID convert(String source) {
        return TSID.from(source);
    }

}
