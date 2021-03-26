package com.templateshop.urlconverter.service;

import com.templateshop.urlconverter.configuration.WebUrlConfigurationProperties;
import com.templateshop.urlconverter.dto.RequestDto;
import com.templateshop.urlconverter.model.UrlConversion;
import com.templateshop.urlconverter.repository.UrlConversionRepository;
import com.templateshop.urlconverter.validator.WebUrlValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class WebUrlConverterServiceTest {

    @InjectMocks
    public WebUrlConverterService webUrlConverterService;

    @Mock
    private UrlConversionRepository urlConversionRepository;

    @Mock
    private WebUrlConfigurationProperties webUrlConfigurationProperties;

    @Mock
    private WebUrlValidator validator;

    private static Map<String, LinkedHashMap<String, String>> parameterMap;

    @Before
    public void init() {
        ReflectionTestUtils.setField(validator, "templateshopHost", "https://www.templateshop.com/");
        ReflectionTestUtils.setField(webUrlConverterService, "pathparameters", Arrays.asList("-p-"));
        ReflectionTestUtils.setField(webUrlConverterService, "homePage", "ty://?Page=Home");
        ReflectionTestUtils.setField(webUrlConverterService, "defaultPrefix", "ty://?Page=/");

        parameterMap = new LinkedHashMap<>();
        LinkedHashMap map = new LinkedHashMap();
        map.put("p", "Product&ContentId");
        map.put("boutiqueId", "CampaignId");
        map.put("merchantId", "MerchantId");
        parameterMap.put("-p-", map);
        map = new LinkedHashMap();
        map.put("q", "Search&Query");
        parameterMap.put("tum--urunler", map);
    }

    @Test
    public void shouldConvertWebUrlToMobileUrl() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("https://www.templateshop.com/caso/erkek-kol-saat-p-1925865?merchantId=105064");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.TRUE);
        when(webUrlConfigurationProperties.getParameterMap()).thenReturn(parameterMap);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        webUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }

    @Test
    public void shouldConvertWebUrlToMobileHomePageUrl() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("https://www.templateshop.com/deneme");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.TRUE);
        when(webUrlConfigurationProperties.getParameterMap()).thenReturn(parameterMap);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        webUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }

    @Test
    public void shouldConvertWebUrlToMobileHomePageUrlWithWrongParameters() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("https://www.templateshop.com/caso/erkek-kol-saat-p-1925865?merchantZd=105064");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.TRUE);
        when(webUrlConfigurationProperties.getParameterMap()).thenReturn(parameterMap);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        webUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }

    @Test
    public void shouldNotConvertWebUrlToMobileUrl() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("wrong_url");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.FALSE);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        webUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }
}