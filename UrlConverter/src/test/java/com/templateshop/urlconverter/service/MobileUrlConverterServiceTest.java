package com.templateshop.urlconverter.service;

import com.templateshop.urlconverter.configuration.MobileUrlConfigurationProperties;
import com.templateshop.urlconverter.dto.RequestDto;
import com.templateshop.urlconverter.model.UrlConversion;
import com.templateshop.urlconverter.repository.UrlConversionRepository;
import com.templateshop.urlconverter.validator.MobileUrlValidator;
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
public class MobileUrlConverterServiceTest {

    @InjectMocks
    public MobileUrlConverterService mobileUrlConverterService;

    @Mock
    private UrlConversionRepository urlConversionRepository;

    @Mock
    private MobileUrlConfigurationProperties mobileUrlConfigurationProperties;

    @Mock
    private MobileUrlValidator validator;

    private static Map<String, LinkedHashMap<String, String>> parameterMap;

    @Before
    public void init() {
        ReflectionTestUtils.setField(validator, "templateshopHost", "ty://?Page=");
        ReflectionTestUtils.setField(mobileUrlConverterService, "pathParameters", Arrays.asList("ContentId","Page"));
        ReflectionTestUtils.setField(mobileUrlConverterService, "homePage", "https://www.templateshop.com/");
        ReflectionTestUtils.setField(mobileUrlConverterService, "defaultPrefix", "https://www.templateshop.com");

        parameterMap = new LinkedHashMap<>();
        LinkedHashMap map = new LinkedHashMap();
        map.put("ContentId", "brand/name-p-");
        map.put("MerchantId", "merchantId");
        map.put("CampaignId", "boutiqueId");
        parameterMap.put("Product", map);
        map = new LinkedHashMap();
        map.put("Query", "q");
        map.put("Page", "tum--urunler");
        parameterMap.put("Search", map);

    }

    @Test
    public void shouldConvertMobileUrlToWebUrl() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.TRUE);
        when(mobileUrlConfigurationProperties.getParameterMap()).thenReturn(parameterMap);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        mobileUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }

    @Test
    public void shouldConvertMobileUrlToWebHomePageUrl() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("ty://?Page=Favorites");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.TRUE);
        when(mobileUrlConfigurationProperties.getParameterMap()).thenReturn(parameterMap);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        mobileUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }

    @Test
    public void shouldConvertMobileUrlToWebHomePageUrlWithWrongParameters() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("ty://?Page=Product&ContentId=1925865&CampaignFd=439892&MerchantId=105064");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.TRUE);
        when(mobileUrlConfigurationProperties.getParameterMap()).thenReturn(parameterMap);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        mobileUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }

    @Test
    public void shouldNotConvertMobileUrlToWebUrl() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("wrong_url");

        when(validator.checkHostName(requestDto.getUrl())).thenReturn(Boolean.FALSE);
        when(urlConversionRepository.save(any(UrlConversion.class))).thenReturn(any(UrlConversion.class));

        mobileUrlConverterService.convert(requestDto);

        verify(urlConversionRepository, times(1)).save(any(UrlConversion.class));
    }
}