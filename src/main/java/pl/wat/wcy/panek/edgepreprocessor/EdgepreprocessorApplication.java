package pl.wat.wcy.panek.edgepreprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EdgepreprocessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgepreprocessorApplication.class, args);
    }

}
