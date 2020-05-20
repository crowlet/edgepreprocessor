package pl.wat.wcy.panek.edgepreprocessor.infrastructure;

import org.springframework.stereotype.Component;
import pl.wat.wcy.panek.edgepreprocessor.config.PublisherIdProvider;

import java.util.UUID;

@Component
public class RandomUuidProvider implements PublisherIdProvider {

    @Override
    public String id() {
        return UUID.randomUUID().toString();
    }
}
