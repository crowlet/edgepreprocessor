package pl.wat.wcy.panek.edgepreprocessor.application;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SpO2Message implements Message<Float> {

    private Float value;
    private LocalDateTime timestamp;
    private String userId;

    public SpO2Message(float value, LocalDateTime timestamp, String userId) {
        this.value = value;
        this.timestamp = timestamp;
        this.userId = userId;
    }
}
