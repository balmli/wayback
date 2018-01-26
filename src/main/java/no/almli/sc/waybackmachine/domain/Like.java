package no.almli.sc.waybackmachine.domain;

import java.util.Date;

import io.searchbox.annotations.JestId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = Like.INDEX_NAME, type = Like.TYPE)
public class Like {

    public static final String INDEX_NAME = "socialcast";
    public static final String TYPE = "likes";

    @Id
    @JestId
    private Long id;
    private Long msgId;
    private Long commentId;
    private Date created;
    private String user;
    private String userId;

    public Like(Long id,
                Long msgId,
                Long commentId,
                Date created,
                String user) {
        this.id = id;
        this.msgId = msgId;
        this.commentId = commentId;
        this.created = created;
        this.user = user;
        this.userId = createUserId(user);
    }

    private String createUserId(String user) {
        return user.toLowerCase().replace(' ', '_');
    }

}
