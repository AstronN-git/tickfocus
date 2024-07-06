package org.astron.tickfocus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimerStateModel {
    private Boolean isTimerStarted;
    private LocalDateTime startDate;
}
