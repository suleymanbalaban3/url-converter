package com.templateshop.urlconverter.service;

import com.templateshop.urlconverter.configuration.WebUrlConfigurationProperties;
import com.templateshop.urlconverter.model.UrlConversion;
import com.templateshop.urlconverter.repository.UrlConversionRepository;
import com.templateshop.urlconverter.validator.WebUrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebUrlConverterService extends AbstractUrlConverterService{

    @Value("#{'${url.web.pathparameters}'.split(';')}")
    private List<String> pathparameters;

    @Value("${url.mobile.base.homepage}")
    private String homePage;

    @Value("${url.mobile.base.default-prefix}")
    private String defaultPrefix;

    private WebUrlConfigurationProperties webUrlConfigurationProperties;

    @Autowired
    public WebUrlConverterService(WebUrlValidator validator,
                                  UrlConversionRepository urlConversionRepository,
                                  WebUrlConfigurationProperties webUrlConfigurationProperties
    ) {
        super(validator, urlConversionRepository);
        this.webUrlConfigurationProperties = webUrlConfigurationProperties;
    }

    @Override
    protected void convertUrl(UrlConversion urlConversion, String url) throws Exception {
        String deeplink = "";
        Map<String, LinkedHashMap<String, String>> parameterMap = webUrlConfigurationProperties.getParameterMap();
        for (String key : parameterMap.keySet()) {
            if (url.contains(key)) {
                deeplink = buildDeeplink(url, parameterMap.get(key));
            }
        }

        urlConversion.setDescription("Success");
        if (ObjectUtils.isEmpty(deeplink)) {
            urlConversion.setResponse(getHomePage());
        } else {
            urlConversion.setResponse(deeplink);
        }
    }

    private String buildDeeplink(String urlString, LinkedHashMap<String, String> parametersMap) throws Exception {
        UriComponents build = UriComponentsBuilder.fromUriString(urlString).build();
        MultiValueMap<String, String> queryParams = build.getQueryParams();
        String path = build.getPath();

        LinkedHashMap<String, String> allParams = extractAllParameters(urlString, queryParams, path);

        return appendAllParameters(parametersMap, allParams);
    }

    private String appendAllParameters(LinkedHashMap<String, String> parametersMap, LinkedHashMap<String, String> allParams) throws Exception {
        StringBuilder sb = new StringBuilder(defaultPrefix);
        Iterator<String> keys = allParams.keySet().iterator();

        while (keys.hasNext()) {
            String key = keys.next();
            String value = allParams.get(key);

            if (!parametersMap.containsKey(key)) {
                throw new Exception();
            }

            String newKey = parametersMap.get(key);
            sb.append(newKey).append("=").append(value);

            if (keys.hasNext()) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

    private LinkedHashMap<String, String> extractAllParameters(String urlString, MultiValueMap<String, String> queryParams, String path) {
        LinkedHashMap<String, String> allParams = new LinkedHashMap<>();

        for (String splitStr : pathparameters) {
            if (urlString.contains(splitStr)) {
                splitStr = splitStr.replace("-", "");
                String cleanedVal = path.replaceAll("[^0-9,]", "");
                allParams.put(splitStr, cleanedVal);
            }
        }
        for (String key : queryParams.keySet()) {
            allParams.put(key, queryParams.getFirst(key));
        }

        return allParams;
    }

    @Override
    public String getHomePage() {
        return homePage;
    }
}
