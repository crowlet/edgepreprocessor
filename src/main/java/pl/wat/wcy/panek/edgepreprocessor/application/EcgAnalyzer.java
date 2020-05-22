package pl.wat.wcy.panek.edgepreprocessor.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcgAnalyzer extends StatisticsAnalyzer {

    public EcgAnalyzer(@Value("${application.dataCapturesSize}") int size) {
        super(size);
    }

    @Override
    public Map<String, Object> statistics() {
        return Map.of("ecg_mean", mean());
    }

    private Map<String, Number> mean() {
        return dataCaptures.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> calculateMean(e.getValue().stream().map(Number::doubleValue).map(Math::abs).collect(Collectors.toList()))));
    }

    private Number calculateMean(Collection<Number> numbers) {
        return numbers.stream().reduce(0, this::sum).doubleValue() / numbers.stream().count();
    }

    private Number sum(Number a, Number b) {
        return a.doubleValue() + b.doubleValue();
    }

    @Override
    public Class<?> applicableMessageClass() {
        return EcgMessage.class;
    }
}
