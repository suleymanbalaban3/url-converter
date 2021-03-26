package com.templateshop.urlconverter.service;

import com.templateshop.urlconverter.dto.RequestDto;
import com.templateshop.urlconverter.model.UrlConversion;
import com.templateshop.urlconverter.repository.UrlConversionRepository;
import com.templateshop.urlconverter.validator.IUrlValidator;

public abstract class AbstractUrlConverterService {

    private IUrlValidator validator;
    private UrlConversionRepository urlConversionRepository;

    public AbstractUrlConverterService(IUrlValidator validator, UrlConversionRepository urlConversionRepository) {
        this.validator = validator;
        this.urlConversionRepository = urlConversionRepository;
    }

    public UrlConversion convert(RequestDto requestDto) {
        UrlConversion urlConversion = new UrlConversion();
        String url = requestDto.getUrl();

        urlConversion.setRequest(url);

        if (!validator.checkHostName(url)) {
            urlConversion.setDescription("Failure! Please provide valid templateshop url.");
        } else {
            try {
                convertUrl(urlConversion, url);
            } catch (Exception e) {
                urlConversion.setResponse(getHomePage());
                urlConversion.setDescription("Failure! Redirect to home page");
            }
        }
        return urlConversionRepository.save(urlConversion);
    }

    abstract void convertUrl(UrlConversion urlConversion, String url) throws Exception;

    abstract String getHomePage();
}
