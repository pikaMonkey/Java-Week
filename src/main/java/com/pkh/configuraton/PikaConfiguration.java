package com.pkh.configuraton;

import com.pkh.dao.prop.PikaDayBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class PikaConfiguration {

    @Bean
    @ConfigurationProperties("pikaday")
    public PikaDayBean getPikaDayBean() {
        return new PikaDayBean();
    }
}
