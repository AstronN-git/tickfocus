package org.astron.tickfocus.model;

import lombok.*;
import org.astron.tickfocus.configuration.TimerSettings;

import java.time.LocalDateTime;

/**
 * Timer Status model represents timer state using State design pattern.
 */
@Data
public class TimerStatusModel {
    @Setter(AccessLevel.NONE)
    private TimerState stoppedState;

    @Setter(AccessLevel.NONE)
    private TimerState workingState;

    @Setter(AccessLevel.NONE)
    private TimerState restingState;

    private TimerState timerState;

    private TimerSettings timerSettings;

    public TimerStatusModel(TimerSettings timerSettings) {
        this.timerSettings = timerSettings;
        stoppedState = new TimerStoppedState(timerSettings);
        workingState = new TimerWorkingState(timerSettings);
        restingState = new TimerRestingState(timerSettings);

        this.timerState = stoppedState;
    }

    public LocalDateTime getStartDate() {
        return timerState.getStartDate();
    }

    public LocalDateTime getEndDate() {
        return timerState.getEndDate();
    }

    public boolean isRunning() {
        return timerState.isRunning();
    }

    public boolean isPrimary() {
        return timerState.isPrimary();
    }

    public String getText() {
        return timerState.getText();
    }

    public void start() {
        timerState.start(this);
    }

    public void stop() {
        timerState.stop(this);
    }

    public void timerEnd() {
        timerState.timerEnd(this);
    }

    void setTimerState(TimerState timerState) {
        this.timerState = timerState;
        this.timerState.stateChanged();
    }
}
