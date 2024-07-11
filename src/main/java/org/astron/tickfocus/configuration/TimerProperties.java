package org.astron.tickfocus.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "timer")
public class TimerProperties {
    private int workingTime = 1500000; // 25 minutes
    private int restingTime = 300000; // 5 minutes
}
