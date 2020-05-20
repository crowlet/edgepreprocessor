package pl.wat.wcy.panek.edgepreprocessor.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttReceiver {

    private final ObjectMapper objectMapper;
    private final IMqttClient client;
    private final MqttListener listener;
    private final String topic;

    public MqttReceiver(ObjectMapper objectMapper, @Qualifier("edgeMqttClient") IMqttClient client, MqttListener listener,
                        @Value("${application.mqtt.edge.topic}") String topic) {
        this.objectMapper = objectMapper;
        this.client = client;
        this.listener = listener;
        this.topic = topic;
    }

    @SneakyThrows
    public void init() {
        client.subscribe(topic, (t, message) -> {
            listener.event(this.objectMapper.readValue(message.getPayload(), HealthMessage.class));
        });
    }

}
