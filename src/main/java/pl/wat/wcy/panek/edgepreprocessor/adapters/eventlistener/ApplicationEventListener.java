package pl.wat.wcy.panek.edgepreprocessor.adapters.eventlistener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.wat.wcy.panek.edgepreprocessor.adapters.messaging.MqttReceiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ApplicationEventListener {

    private final AtomicBoolean done;
    private final ExecutorService executors;
    private final MqttReceiver mqttReceiver;

    public ApplicationEventListener(MqttReceiver mqttReceiver) {
        this.done = new AtomicBoolean(false);
        this.executors = Executors.newSingleThreadExecutor();
        this.mqttReceiver = mqttReceiver;
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event){
        if(!done.get()) {
            done.set(true);
            this.executors.submit(mqttReceiver::init);
        }
    }


}
