package org.astron.tickfocus.model;

import org.astron.tickfocus.configuration.TimerSettings;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

class TimerWorkingState implements TimerState {
    LocalDateTime startDate;
    TimerSettings timerSettings;

    public TimerWorkingState(TimerSettings timerSettings) {
        this.timerSettings = timerSettings;
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public LocalDateTime getEndDate() {
        return startDate.plus(timerSettings.getWorkingTime(), ChronoUnit.MILLIS);
    }

    @Override
    public boolean isPrimary() {
        return true;
    }

    @Override
    public String getText() {
        return "Working...";
    }

    @Override
    public void stateChanged() {
        this.startDate = LocalDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public void start(TimerStatusModel timerStatusModel) {
        throw new IllegalStateException("cannot start timer, it is already running");
    }

    @Override
    public void stop(TimerStatusModel timerStatusModel) {
        timerStatusModel.setTimerState(timerStatusModel.getStoppedState());
    }

    @Override
    public void timerEnd(TimerStatusModel timerStatusModel) {
        timerStatusModel.setTimerState(timerStatusModel.getRestingState());
    }
}
