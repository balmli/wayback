package no.almli.sc.waybackmachine.config;

import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder(TimeZone timeZone) {
        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        b.modulesToInstall(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        b.featuresToDisable(WRITE_DATES_AS_TIMESTAMPS);
        b.timeZone(timeZone);
        b.serializationInclusion(JsonInclude.Include.NON_NULL);
        return b;
    }
    
}
