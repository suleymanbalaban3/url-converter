package com.templateshop.urlconverter.controller;

import com.templateshop.urlconverter.dto.RequestDto;
import com.templateshop.urlconverter.model.UrlConversion;
import com.templateshop.urlconverter.service.MobileUrlConverterService;
import com.templateshop.urlconverter.service.WebUrlConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url-converter")
public class UrlConverterController {

    @Autowired
    private WebUrlConverterService webUrlConverterService;

    @Autowired
    private MobileUrlConverterService mobileWebUrlConverterService;

    @PostMapping("/web-url-to-mobile-url")
    public UrlConversion convertToWebUrl(@RequestBody RequestDto requestDto) {
        return webUrlConverterService.convert(requestDto);
    }

    @PostMapping("/mobile-url-to-web-url")
    public UrlConversion convertToMobileUrl(@RequestBody RequestDto requestDto) {
        return mobileWebUrlConverterService.convert(requestDto);
    }
}
