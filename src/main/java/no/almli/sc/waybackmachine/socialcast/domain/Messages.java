package no.almli.sc.waybackmachine.socialcast.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Messages {
    private List<Message> messages;
}
