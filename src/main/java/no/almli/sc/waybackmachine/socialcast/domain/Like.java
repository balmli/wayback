package no.almli.sc.waybackmachine.socialcast.domain;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {
    private Long id;
    private LikeUser user;
    private ZonedDateTime created_at;
    private Long likes_count;
}
