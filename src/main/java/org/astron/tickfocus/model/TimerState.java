package org.astron.tickfocus.model;

import java.time.LocalDateTime;

interface TimerState {
    boolean isRunning();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    boolean isPrimary();
    String getText();

    default void stateChanged() {}

    void start(TimerStatusModel timerStatusModel);
    void stop(TimerStatusModel timerStatusModel);
    void timerEnd(TimerStatusModel timerStatusModel);
}
