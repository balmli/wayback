package no.almli.sc.waybackmachine.domain;

import java.util.Date;

import io.searchbox.annotations.JestId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = Message.INDEX_NAME, type = Message.TYPE)
public class Message {

    public static final String INDEX_NAME = "socialcast";
    public static final String TYPE = "messages";

    @Id
    @JestId
    private Long id;
    private String title;
    private String body;
    private String htmlBody;
    private Date created;
    private Date updated;
    private String user;
    private String userId;
    private Long commentsCount;
    private Long likesCount;

    public Message(Long id,
                   String title,
                   String body,
                   String htmlBody,
                   Date created,
                   Date updated,
                   String user,
                   Long commentsCount,
                   Long likesCount
                   ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.htmlBody = htmlBody;
        this.created = created;
        this.updated = updated;
        this.user = user;
        this.userId = createUserId(user);
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
    }

    private String createUserId(String user) {
        return user.toLowerCase().replace(' ', '_');
    }

}
