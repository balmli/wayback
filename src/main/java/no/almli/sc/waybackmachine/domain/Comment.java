package no.almli.sc.waybackmachine.domain;

import java.util.Date;

import io.searchbox.annotations.JestId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = Comment.INDEX_NAME, type = Comment.TYPE)
public class Comment {

    public static final String INDEX_NAME = "socialcast";
    public static final String TYPE = "comments";

    @Id
    @JestId
    private Long id;
    private Long msgId;
    private String text;
    private Date created;
    private Date updated;
    private String user;
    private String userId;
    private Long likesCount;

    public Comment(Long id,
                   Long msgId,
                   String text,
                   Date created,
                   Date updated,
                   String user,
                   Long likesCount) {
        this.id = id;
        this.msgId = msgId;
        this.text = text;
        this.created = created;
        this.updated = updated;
        this.user = user;
        this.userId = createUserId(user);
        this.likesCount = likesCount;
    }

    private String createUserId(String user) {
        return user.toLowerCase().replace(' ', '_');
    }

}
