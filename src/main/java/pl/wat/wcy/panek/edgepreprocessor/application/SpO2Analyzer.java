package pl.wat.wcy.panek.edgepreprocessor.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.wat.wcy.panek.edgepreprocessor.infrastructure.CyclicTimeAwareList;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpO2Analyzer extends StatisticsAnalyzer {

    public SpO2Analyzer(@Value("${application.dataCapturesSize}") int size) {
        super(size);
    }

    @Override
    public Map<String, Object> statistics() {
        return Map.of("spo2_95p", percentiles(95));
    }

    private Map<String, Number> percentiles(int p) {
        return dataCaptures.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                e -> e.getKey(),
                                e -> calculatePercentile(e.getValue(), p)
                        )
                );
    }

    private Number calculatePercentile(CyclicTimeAwareList<Number> dataCaptures, int p) {
        if(CollectionUtils.isEmpty(dataCaptures)) {
            return -1;
        }
        var values = dataCaptures.sortedCopy();
        return values.get((p * dataCaptures.size()) / 100);
    }

    @Override
    public Class<?> applicableMessageClass() {
        return SpO2Message.class;
    }

}
