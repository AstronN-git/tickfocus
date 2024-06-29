package org.astron.tickfocus.controller;

import org.astron.tickfocus.dto.TimerState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/workspace")
@SessionAttributes("timerState")
public class WorkspaceController {
    @GetMapping
    public String showHomeView() {
        return "workspace";
    }

    @GetMapping("/startTimer")
    public String startTimer(@ModelAttribute("timerState") TimerState timerState) {
        timerState.setIsTimerStarted(true);
        return "redirect:/workspace";
    }

    @GetMapping("/endTimer")
    public String stopTimer(@ModelAttribute("timerState") TimerState timerState) {
        timerState.setIsTimerStarted(false);
        return "redirect:/workspace";
    }

    @ModelAttribute("timerState")
    private TimerState timerState() {
        return new TimerState(false);
    }
}
