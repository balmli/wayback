package no.almli.sc.waybackmachine.socialcast.domain;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private Long id;
    private String text;
    private User user;
    private ZonedDateTime created_at;
    private ZonedDateTime updated_at;
    private List<Like> likes;
    private Long likes_count;
}
