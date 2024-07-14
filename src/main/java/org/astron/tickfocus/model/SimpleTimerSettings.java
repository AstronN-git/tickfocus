package org.astron.tickfocus.model;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.astron.tickfocus.configuration.TimerSettings;

@Getter
@Setter
public class SimpleTimerSettings implements TimerSettings {
    @Positive(message = "Time should be greater than zero.")
    private int workingTime;

    @Positive(message = "Time should be greater than zero.")
    private int restingTime;
}
