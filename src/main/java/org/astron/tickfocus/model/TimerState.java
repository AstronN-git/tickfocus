package org.astron.tickfocus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimerState {
    private Boolean isTimerStarted;
    private Date startDate;
}
