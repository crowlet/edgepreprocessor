package pl.wat.wcy.panek.edgepreprocessor.adapters.messaging;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HealthMessage {
    private String type;
    private float value;
    private LocalDateTime timestamp;
    private String userId;
}
