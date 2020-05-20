package pl.wat.wcy.panek.edgepreprocessor.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EcgAnalyzer extends StatisticsAnalyzer {

    public EcgAnalyzer(@Value("${application.dataCapturesSize}") int size) {
        super(size);
    }

    @Override
    public Class<?> applicableMessageClass() {
        return EcgMessage.class;
    }
}
