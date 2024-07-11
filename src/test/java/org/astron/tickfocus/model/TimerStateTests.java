package org.astron.tickfocus.model;

import org.astron.tickfocus.configuration.TimerProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimerStateTests {
    @Test
    void testStateMachineWorksCorrectly() {
        TimerStatusModel timerStatusModel = new TimerStatusModel(new TimerProperties());

        assertEquals(timerStatusModel.getTimerState(), timerStatusModel.getStoppedState());

        timerStatusModel.start();

        assertEquals(timerStatusModel.getTimerState(), timerStatusModel.getWorkingState());

        timerStatusModel.setTimerState(timerStatusModel.getWorkingState());
        timerStatusModel.stop();

        assertEquals(timerStatusModel.getTimerState(), timerStatusModel.getStoppedState());

        timerStatusModel.setTimerState(timerStatusModel.getWorkingState());
        timerStatusModel.timerEnd();

        assertEquals(timerStatusModel.getTimerState(), timerStatusModel.getRestingState());

        timerStatusModel.setTimerState(timerStatusModel.getRestingState());
        timerStatusModel.stop();

        assertEquals(timerStatusModel.getTimerState(), timerStatusModel.getStoppedState());

        timerStatusModel.setTimerState(timerStatusModel.getRestingState());
        timerStatusModel.timerEnd();

        assertEquals(timerStatusModel.getTimerState(), timerStatusModel.getWorkingState());
    }

    @Test
    void testIllegalActionOnWorkingState() {
        TimerStatusModel timerStatusModel = new TimerStatusModel(new TimerProperties());
        timerStatusModel.start();

        assertThrows(IllegalStateException.class, timerStatusModel::start);
    }

    @Test
    void testIllegalActionOnRestingState() {
        TimerStatusModel timerStatusModel = new TimerStatusModel(new TimerProperties());
        timerStatusModel.setTimerState(timerStatusModel.getRestingState());

        assertThrows(IllegalStateException.class, timerStatusModel::start);
    }

    @Test
    void testIllegalActionsOnStoppedState() {
        TimerStatusModel timerStatusModel = new TimerStatusModel(new TimerProperties());
        timerStatusModel.setTimerState(timerStatusModel.getStoppedState());

        assertThrows(IllegalStateException.class, timerStatusModel::stop);
        assertThrows(IllegalStateException.class, timerStatusModel::timerEnd);
    }
}
