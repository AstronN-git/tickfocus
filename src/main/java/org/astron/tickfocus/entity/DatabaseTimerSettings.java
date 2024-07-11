package org.astron.tickfocus.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.astron.tickfocus.configuration.TimerSettings;

@Data
@Embeddable
public class DatabaseTimerSettings implements TimerSettings {
    @NotNull
    private int workingTime;

    @NotNull
    private int restingTime;
}
