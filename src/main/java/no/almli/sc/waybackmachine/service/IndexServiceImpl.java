package no.almli.sc.waybackmachine.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import lombok.extern.slf4j.Slf4j;
import no.almli.sc.waybackmachine.domain.Comment;
import no.almli.sc.waybackmachine.domain.Message;
import no.almli.sc.waybackmachine.domain.Like;
import no.almli.sc.waybackmachine.socialcast.domain.Messages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    private final ObjectMapper objectMapper;
    private final JestClient jestClient;

    @Value("${socialcast.jsondir}")
    private String jsonDir;

    public IndexServiceImpl(ObjectMapper objectMapper,
                            JestClient jestClient) {
        this.objectMapper = objectMapper;
        this.jestClient = jestClient;
    }

    @Override
    public void indexJsonMessages() {
        List<Path> files = null;
        try {
            files = Files.list(Paths.get(jsonDir))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().contains("sc_"))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("error listing files", e);
        }
        if (files != null) {
            files.stream()
                    .map(this::parseJson)
                    .filter(Objects::nonNull)
                    .forEach(this::indexMessages);
        }
    }

    private Messages parseJson(Path file) {
        Messages msgs = null;
        try {
            msgs = objectMapper.readValue(Files.readAllBytes(file), Messages.class);
            log.info("parsed {}", file.toAbsolutePath());
        } catch (IOException e) {
            log.error("Error parsing file: {}", file, e);
        }
        return msgs;
    }

    private boolean indexMessages(Messages msgs) {
        if (msgs != null && msgs.getMessages() != null && msgs.getMessages().size() > 0) {
            msgs.getMessages().forEach(msg -> {
                try {
                    jestClient.execute(
                            new Index.Builder(
                                    new Message(msg.getId(), msg.getTitle(), msg.getBody(), msg.getHtml_body(),
                                            toDate(msg.getCreated_at()), toDate(msg.getUpdated_at()),
                                            msg.getUser() != null ? msg.getUser().getName() : null,
                                            msg.getComments_count(), msg.getLikes_count()
                                    ))
                                    .index(Message.INDEX_NAME)
                                    .type(Message.TYPE)
                                    .build()
                    );
                } catch (IOException e) {
                    log.error("Error indexing message", e);
                }
                if (msg.getLikes() != null) {
                    msg.getLikes().forEach(l -> {
                        try {
                            jestClient.execute(
                                    new Index.Builder(
                                            new Like(l.getId(),
                                                    msg.getId(),
                                                    null,
                                                    toDate(l.getCreated_at()),
                                                    l.getUser() != null ? l.getUser().getName() : null
                                            ))
                                            .index(Like.INDEX_NAME)
                                            .type(Like.TYPE)
                                            .build()
                            );
                        } catch (IOException e) {
                            log.error("Error indexing like on message", e);
                        }
                    });
                }
                if (msg.getComments() != null) {
                    msg.getComments().forEach(c -> {
                        try {
                            jestClient.execute(
                                    new Index.Builder(
                                            new Comment(c.getId(), msg.getId(), c.getText(),
                                                    toDate(c.getCreated_at()), toDate(c.getUpdated_at()),
                                                    c.getUser() != null ? c.getUser().getName() : null,
                                                    c.getLikes_count()
                                            ))
                                            .index(Comment.INDEX_NAME)
                                            .type(Comment.TYPE)
                                            .build()
                            );
                        } catch (IOException e) {
                            log.error("Error indexing comment", e);
                        }
                        if (c.getLikes() != null) {
                            c.getLikes().forEach(l -> {
                                try {
                                    jestClient.execute(
                                            new Index.Builder(
                                                    new Like(l.getId(),
                                                            msg.getId(),
                                                            c.getId(),
                                                            toDate(l.getCreated_at()),
                                                            l.getUser() != null ? l.getUser().getName() : null
                                                    ))
                                                    .index(Like.INDEX_NAME)
                                                    .type(Like.TYPE)
                                                    .build()
                                    );
                                } catch (IOException e) {
                                    log.error("Error indexing like on comment", e);
                                }
                            });
                        }
                    });
                }
            });
            return true;
        }
        return false;
    }

    private Date toDate(ZonedDateTime zdt) {
        return zdt != null ? Date.from(zdt.toInstant()) : null;
    }

}