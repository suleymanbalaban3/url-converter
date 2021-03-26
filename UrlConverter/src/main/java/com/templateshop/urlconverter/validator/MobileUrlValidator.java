package com.templateshop.urlconverter.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MobileUrlValidator implements IUrlValidator {

    @Value("${url.mobile.validate.templateShop-host}")
    private String templateshopHost;

    public boolean checkHostName(String url){
        return url.contains(templateshopHost);
    }
}
