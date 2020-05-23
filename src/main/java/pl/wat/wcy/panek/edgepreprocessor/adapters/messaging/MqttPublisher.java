package pl.wat.wcy.panek.edgepreprocessor.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.wat.wcy.panek.edgepreprocessor.application.MessagePublisher;

@Component
public class MqttPublisher implements MessagePublisher {

    private final IMqttClient mqttClient;
    private final ObjectMapper objectMapper;
    private final String topic;

    public MqttPublisher(
            @Qualifier("cloudMqttClient") IMqttClient mqttClient,
            ObjectMapper objectMapper,
            @Value("${application.mqtt.cloud.topic}") String topic
    ) {
        this.mqttClient = mqttClient;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    @SneakyThrows
    @Override
    public void send(Object message) {
        mqttClient.publish(topic, mqttMessage(message));
    }

    @SneakyThrows
    private MqttMessage mqttMessage(Object message) {
        MqttMessage mqttMessage = new MqttMessage(objectMapper.writeValueAsBytes(message));
        mqttMessage.setRetained(true);
        mqttMessage.setQos(1);
        return mqttMessage;
    }
}
