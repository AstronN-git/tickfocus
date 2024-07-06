package org.astron.tickfocus.controller;

import lombok.extern.slf4j.Slf4j;
import org.astron.tickfocus.configuration.TimerProperties;
import org.astron.tickfocus.entity.TimerState;
import org.astron.tickfocus.entity.User;
import org.astron.tickfocus.model.TimerStateModel;
import org.astron.tickfocus.repository.TimerStateRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/workspace")
@SessionAttributes("timerState")
public class WorkspaceController {
    private final TimerProperties timerProperties;
    private final TimerStateRepository timerStateRepository;

    public WorkspaceController(TimerProperties timerProperties, TimerStateRepository timerStateRepository) {
        this.timerProperties = timerProperties;
        this.timerStateRepository = timerStateRepository;
    }

    @GetMapping
    public String showHomeView(
            @AuthenticationPrincipal User user,
            @ModelAttribute("timerState") TimerStateModel timerStateModel
    ) {
        if (user != null) {
            TimerState timerState = timerStateRepository.findByUser(user);
            if (timerState.getTimerStarted() != null) {
                timerStateModel.setIsTimerStarted(true);
                timerStateModel.setStartDate(timerState.getTimerStarted());
            } else {
                timerStateModel.setIsTimerStarted(false);
                timerStateModel.setStartDate(null);
            }
        }
        return "workspace";
    }

    @GetMapping("/startTimer")
    public String startTimer(
            @ModelAttribute("timerState") TimerStateModel timerStateModel,
            @AuthenticationPrincipal User user
    ) {
        timerStateModel.setIsTimerStarted(true);
        timerStateModel.setStartDate(LocalDateTime.now());

        if (user != null)
            timerStateRepository.updateTimerStartedByUser(timerStateModel.getStartDate(), user);

        return "redirect:/workspace";
    }

    @GetMapping("/endTimer")
    public String stopTimer(
            @ModelAttribute("timerState") TimerStateModel timerStateModel,
            @AuthenticationPrincipal User user
    ) {
        timerStateModel.setIsTimerStarted(false);
        timerStateModel.setStartDate(null);

        if (user != null)
            timerStateRepository.updateTimerStartedByUser(null, user);
        return "redirect:/workspace";
    }

    @ModelAttribute("timerState")
    public TimerStateModel timerState() {
        return new TimerStateModel(false, null);
    }

    @ModelAttribute("timerProperties")
    public TimerProperties timerProperties() {
        return timerProperties;
    }
}
