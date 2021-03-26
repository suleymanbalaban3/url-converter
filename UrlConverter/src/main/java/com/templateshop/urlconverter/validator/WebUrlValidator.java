package com.templateshop.urlconverter.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebUrlValidator implements IUrlValidator {

    @Value("${url.web.validate.templateShop-host}")
    private String templateshopHost;

    public boolean checkHostName(String url){
        return url.contains(templateshopHost);
    }
}
