package pl.wat.wcy.panek.edgepreprocessor.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MqttConfig {


    @Bean("edgeMqttClient")
    IMqttClient edgeMqttClient(
            @Value("${application.mqtt.edge.protocol}://${application.mqtt.edge.host}:${application.mqtt.edge.port}")
            final String mqttBrokerUrl,
            final PublisherIdProvider publisherIdProvider,
            final MqttConnectOptions options
    ) throws MqttException {
        log.info("Edge: " + mqttBrokerUrl);
        var mqttClient = new MqttClient(mqttBrokerUrl, publisherIdProvider.id());
        mqttClient.connect(options);
        return mqttClient;
    }

    @Bean("cloudMqttClient")
    IMqttClient cloudMqttClient(
            @Value("${application.mqtt.cloud.protocol}://${application.mqtt.cloud.host}:${application.mqtt.cloud.port}")
            final String mqttBrokerUrl,
            final PublisherIdProvider publisherIdProvider,
            final MqttConnectOptions options
    ) throws MqttException {
        log.info("Cloud: " + mqttBrokerUrl);
        var mqttClient = new MqttClient(mqttBrokerUrl, publisherIdProvider.id());
        mqttClient.connect(options);
        return mqttClient;
    }

    @Bean
    MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }
}
