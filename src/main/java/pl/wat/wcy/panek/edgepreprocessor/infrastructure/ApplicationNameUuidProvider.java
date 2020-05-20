package pl.wat.wcy.panek.edgepreprocessor.infrastructure;

import org.springframework.stereotype.Component;
import pl.wat.wcy.panek.edgepreprocessor.EdgepreprocessorApplication;
import pl.wat.wcy.panek.edgepreprocessor.config.PublisherIdProvider;

@Component
public class ApplicationNameUuidProvider implements PublisherIdProvider {

    @Override
    public String id() {
        return EdgepreprocessorApplication.class.getSimpleName();
    }
}
