package pl.wat.wcy.panek.edgepreprocessor.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpO2Analyzer extends StatisticsAnalyzer {



    public SpO2Analyzer(@Value("${application.dataCapturesSize}") int size) {
        super(size);
    }

    @Override
    public Class<?> applicableMessageClass() {
        return SpO2Message.class;
    }

}
