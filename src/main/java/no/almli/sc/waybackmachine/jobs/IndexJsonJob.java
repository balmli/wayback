package no.almli.sc.waybackmachine.jobs;

import no.almli.sc.waybackmachine.service.IndexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IndexJsonJob {

    private final IndexService indexService;

    @Value("${indexjsonjob.enabled}")
    private Boolean jobEnabled;

    public IndexJsonJob(IndexService indexService) {
        this.indexService = indexService;
    }

    @Scheduled(fixedDelay = 365 * 86400 * 1000L, initialDelay = 5000)
    public void run() {
        if (jobEnabled) {
            indexService.indexJsonMessages();
        }
    }
}
