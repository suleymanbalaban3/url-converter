package com.trendyol.urlconverter;

import com.trendyol.urlconverter.configuration.MobileUrlConfigurationProperties;
import com.trendyol.urlconverter.configuration.WebUrlConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({WebUrlConfigurationProperties.class, MobileUrlConfigurationProperties.class})
@SpringBootApplication
public class UrlconverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlconverterApplication.class, args);
    }

}
