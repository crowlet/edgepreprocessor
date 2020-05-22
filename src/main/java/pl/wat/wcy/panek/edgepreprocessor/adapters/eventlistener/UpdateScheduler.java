package pl.wat.wcy.panek.edgepreprocessor.adapters.eventlistener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.edgepreprocessor.adapters.messaging.MqttPublisher;
import pl.wat.wcy.panek.edgepreprocessor.application.StatisticsAnalyzer;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateScheduler {

    private final List<StatisticsAnalyzer> statisticsAnalyzers;
    private final MqttPublisher publisher;

    @Scheduled(cron = "${application.updateCron}")
    public void update() {
        var map = statisticsAnalyzers.stream()
                .collect(Collectors.toMap(a -> a.applicableMessageClass().getSimpleName(), StatisticsAnalyzer::statistics));
        log.info(map.toString());
        publisher.send(map);
    }
}
