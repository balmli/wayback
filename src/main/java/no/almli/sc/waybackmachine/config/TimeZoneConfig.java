package no.almli.sc.waybackmachine.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeZoneConfig {

    @Bean
    public TimeZone timezone() {
        return TimeZone.getTimeZone("Europe/Oslo");
    }

}
