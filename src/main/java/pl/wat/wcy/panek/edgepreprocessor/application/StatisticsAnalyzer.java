package pl.wat.wcy.panek.edgepreprocessor.application;

import org.springframework.util.CollectionUtils;
import pl.wat.wcy.panek.edgepreprocessor.infrastructure.CyclicTimeAwareList;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class StatisticsAnalyzer {

    public abstract Class<?> applicableMessageClass();

    protected final Map<String, CyclicTimeAwareList<Number>> dataCaptures;
    protected final Integer size;

    public StatisticsAnalyzer(Integer size) {
        dataCaptures = new HashMap<>();
        this.size = size;
    }

    public void apply(Message<? extends Number> message) {
        getDataCaptures(message.getUserId()).add(message.getValue());
    }
    private CyclicTimeAwareList<Number> getDataCaptures(String userId) {
        if (dataCaptures.containsKey(userId)) {
            return dataCaptures.get(userId);
        } else {
            var dataCaptures = new CyclicTimeAwareList<Number>(size);
            this.dataCaptures.put(userId, dataCaptures);
            return dataCaptures;
        }
    }

    public Map<String, Number> percentiles(int p) {
        return dataCaptures.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> calculatePercentile(e.getValue(), p)));
    }

    private Number calculatePercentile(CyclicTimeAwareList<Number> dataCaptures, int p) {
        if(CollectionUtils.isEmpty(dataCaptures)) {
            return -1;
        }
        var values = dataCaptures.sortedCopy();
        return values.get((p * dataCaptures.size()) / 100);
    }
}
