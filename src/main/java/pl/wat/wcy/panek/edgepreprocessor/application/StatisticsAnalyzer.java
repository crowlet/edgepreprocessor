package pl.wat.wcy.panek.edgepreprocessor.application;

import org.springframework.util.CollectionUtils;
import pl.wat.wcy.panek.edgepreprocessor.infrastructure.CyclicTimeAwareList;

import java.util.HashMap;
import java.util.Map;

public abstract class StatisticsAnalyzer {

    public abstract Class<?> applicableMessageClass();

    protected final Map<String, CyclicTimeAwareList<Number>> dataCaptures;
    protected final Integer size;

    public abstract Map<String, Object> statistics();

    public StatisticsAnalyzer(Integer size) {
        dataCaptures = new HashMap<>();
        this.size = size;
    }

    public void apply(Message<? extends Number> message) {
        getDataCaptures(message.getUserId()).add(message.getValue());
    }

    protected CyclicTimeAwareList<Number> getDataCaptures(String userId) {
        if (dataCaptures.containsKey(userId)) {
            return dataCaptures.get(userId);
        } else {
            var dataCaptures = new CyclicTimeAwareList<Number>(size);
            this.dataCaptures.put(userId, dataCaptures);
            return dataCaptures;
        }
    }
}
