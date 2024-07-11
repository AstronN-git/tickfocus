package org.astron.tickfocus.model;

import org.astron.tickfocus.configuration.TimerProperties;

import java.time.LocalDateTime;

class TimerStoppedState implements TimerState {
    TimerProperties timerProperties;

    public TimerStoppedState(TimerProperties timerProperties) {
        this.timerProperties = timerProperties;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public LocalDateTime getStartDate() {
        return null;
    }

    @Override
    public LocalDateTime getEndDate() {
        return null;
    }

    @Override
    public boolean isPrimary() {
        return true;
    }

    @Override
    public String getText() {
        return "Resting...";
    }

    @Override
    public void start(TimerStatusModel timerStatusModel) {
        timerStatusModel.setTimerState(timerStatusModel.getWorkingState());
    }

    @Override
    public void stop(TimerStatusModel timerStatusModel) {
        throw new IllegalStateException("cannot stop timer, it is already stopped");
    }

    @Override
    public void timerEnd(TimerStatusModel timerStatusModel) {
        throw new IllegalStateException("timer is stopped");
    }
}
