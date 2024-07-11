package org.astron.tickfocus.model;

import org.astron.tickfocus.configuration.TimerProperties;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

class TimerWorkingState implements TimerState {
    LocalDateTime startDate;
    TimerProperties timerProperties;

    public TimerWorkingState(TimerProperties timerProperties) {
        this.timerProperties = timerProperties;
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
        return startDate.plus(timerProperties.getWorkingTime(), ChronoUnit.MILLIS);
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
