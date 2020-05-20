package pl.wat.wcy.panek.edgepreprocessor.adapters.eventlistener;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.edgepreprocessor.adapters.messaging.MqttPublisher;
import pl.wat.wcy.panek.edgepreprocessor.application.StatisticsAnalyzer;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateScheduler {

    private final List<StatisticsAnalyzer> statisticsAnalyzers;
    private final MqttPublisher publisher;

    @Scheduled(cron = "${application.updateCron}")
    public void update() {
        var map = statisticsAnalyzers.stream().collect(Collectors.toMap(a -> a.applicableMessageClass().getSimpleName(), a -> a.percentiles(95)));
        System.out.println(map);
        publisher.send(map);
    }
}
