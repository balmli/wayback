package no.almli.sc.waybackmachine.socialcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private Long id;
    private String first_name;
    private String last_name;
    private String name;
}
