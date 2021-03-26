package com.templateshop.urlconverter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "url.mobile.map")
public class MobileUrlConfigurationProperties {

    private Map<String,LinkedHashMap<String, String>>  parameterMap;

    public MobileUrlConfigurationProperties(Map<String,LinkedHashMap<String, String>>  parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String,LinkedHashMap<String, String>>  getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String,LinkedHashMap<String, String>>  parameterMap) {
        this.parameterMap = parameterMap;
    }
}
