package com.templateshop.urlconverter.service;

import com.templateshop.urlconverter.configuration.MobileUrlConfigurationProperties;
import com.templateshop.urlconverter.model.UrlConversion;
import com.templateshop.urlconverter.repository.UrlConversionRepository;
import com.templateshop.urlconverter.validator.MobileUrlValidator;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MobileUrlConverterService extends AbstractUrlConverterService {

    @Value("${url.web.base.homepage}")
    private String homePage;

    @Value("${url.web.base.default-prefix}")
    private String defaultPrefix;

    @Value("${url.mobile.path-parameters}")
    private List<String> pathParameters;

    @Value("${url.mobile.path-segments}")
    private List<String> pathSegments;

    private MobileUrlConfigurationProperties mobileUrlConfigurationProperties;

    @Autowired
    public MobileUrlConverterService(MobileUrlValidator validator, UrlConversionRepository urlConversionRepository, MobileUrlConfigurationProperties mobileUrlConfigurationProperties) {
        super(validator, urlConversionRepository);
        this.mobileUrlConfigurationProperties = mobileUrlConfigurationProperties;
    }

    @Override
    public void convertUrl(UrlConversion urlConversion, String url) throws Exception {
        String webUrl = "";
        Map<String, LinkedHashMap<String, String>> parameterMap = mobileUrlConfigurationProperties.getParameterMap();

        for (String key : parameterMap.keySet()) {
            if (url.contains(key)) {

                webUrl = buildWebUrl(url, parameterMap.get(key));
            }
        }

        urlConversion.setDescription("Success");
        if (ObjectUtils.isEmpty(webUrl)) {
            urlConversion.setResponse(getHomePage());
        } else {
            urlConversion.setResponse(webUrl);
        }
    }

    @Override
    public String getHomePage() {
        return homePage;
    }

    private String buildWebUrl(String urlString, LinkedHashMap<String, String> param) throws Exception {
        UriComponents build = UriComponentsBuilder.fromUriString(urlString).build();
        MultiValueMap<String, String> queryParams = build.getQueryParams();

        return appendAllParameters(param, queryParams);

    }

    private String appendAllParameters(LinkedHashMap<String, String> param, MultiValueMap<String, String> queryParams) throws Exception {

        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost(defaultPrefix);

        for (String key : queryParams.keySet()) {
            if (param.containsKey(key)) {
                if (pathParameters.contains(key)) {
                    builder.setPath(param.get(key) + queryParams.getFirst(key));
                }
                else if(pathSegments.contains(key)){
                        builder.setPathSegments(param.get(key));
                }
                else {
                    builder.addParameter(param.get(key), queryParams.getFirst(key));
                }
            } else if (!key.equals("Page")) {
                throw new Exception();
            }
        }
        return builder.build().toURL().toString();
    }

}
