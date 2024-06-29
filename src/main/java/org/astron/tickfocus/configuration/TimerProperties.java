package org.astron.tickfocus.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "timer")
public class TimerProperties {
    private int sessionTime = 120000;
}
