package pl.wat.wcy.panek.edgepreprocessor.adapters.messaging;

import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.edgepreprocessor.application.EcgMessage;
import pl.wat.wcy.panek.edgepreprocessor.application.Message;
import pl.wat.wcy.panek.edgepreprocessor.application.SpO2Message;
import pl.wat.wcy.panek.edgepreprocessor.application.StatisticsAnalyzer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MqttListener {

    private Map<String, StatisticsAnalyzer> services;

    public MqttListener(List<StatisticsAnalyzer> services) {
        this.services = services.stream().collect(Collectors.toMap(s -> s.applicableMessageClass().getSimpleName(), s -> s));
    }

    public void event(HealthMessage message) {
        dispatch(message);
    }

    private void dispatch(HealthMessage message) {
        services.get(message.getType()).apply(map(message));
    }

    private Message<? extends Number> map(HealthMessage healthMessage) {
        if (healthMessage.getType().endsWith(SpO2Message.class.getSimpleName())) {
            return spO2Message(healthMessage);
        } else if (healthMessage.getType().endsWith(EcgMessage.class.getSimpleName())) {
            return ecgMessage(healthMessage);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private SpO2Message spO2Message(HealthMessage message) {
        return new SpO2Message(message.getValue(), message.getTimestamp(), message.getUserId());
    }

    private EcgMessage ecgMessage(HealthMessage message) {
        return new EcgMessage(message.getValue(), message.getTimestamp(), message.getUserId());
    }


}
