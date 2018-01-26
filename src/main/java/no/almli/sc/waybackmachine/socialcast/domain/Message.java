package no.almli.sc.waybackmachine.socialcast.domain;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private Long id;
    private String title;
    private String body;
    private String html_body;
    private ZonedDateTime created_at;
    private ZonedDateTime updated_at;
    private User user;
    private List<Like> likes;
    private List<Comment> comments;
    private Long comments_count;
    private Long likes_count;
}
