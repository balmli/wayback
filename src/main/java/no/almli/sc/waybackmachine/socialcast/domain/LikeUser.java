package no.almli.sc.waybackmachine.socialcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeUser {
    private Long id;
    private String name;
}
