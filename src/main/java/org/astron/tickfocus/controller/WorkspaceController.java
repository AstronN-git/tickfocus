package org.astron.tickfocus.controller;

import lombok.extern.slf4j.Slf4j;
import org.astron.tickfocus.configuration.DefaultTimerSettings;
import org.astron.tickfocus.configuration.TimerSettings;
import org.astron.tickfocus.entity.User;
import org.astron.tickfocus.model.TimerStatusModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/workspace")
@SessionAttributes("timerStatus")
public class WorkspaceController {
    private final DefaultTimerSettings defaultTimerSettings;

    public WorkspaceController(DefaultTimerSettings defaultTimerSettings) {
        this.defaultTimerSettings = defaultTimerSettings;
    }

    @GetMapping
    public String showHomeView(
            @ModelAttribute("timerStatus") TimerStatusModel timerStatusModel
    ) {
        log.debug("/workspace endpoint");
        return "workspace";
    }

    @GetMapping("/startTimer")
    public String startTimer(
            @ModelAttribute("timerStatus") TimerStatusModel timerStatusModel
    ) {
        log.debug("/workspace/startTimer endpoint");
        timerStatusModel.start();
        return "redirect:/workspace";
    }

    @GetMapping("/stopTimer")
    public String stopTimer(
            @ModelAttribute("timerStatus") TimerStatusModel timerStatusModel
    ) {
        log.debug("/workspace/stopTimer endpoint");
        timerStatusModel.stop();
        return "redirect:/workspace";
    }

    @GetMapping("/endTimer")
    public String endTimer(
            @ModelAttribute("timerStatus") TimerStatusModel timerStatusModel
    ) {
        log.debug("/workspace/endTimer endpoint");
        timerStatusModel.timerEnd();
        return "redirect:/workspace";
    }

    @ModelAttribute("timerStatus")
    public TimerStatusModel timerState(@AuthenticationPrincipal User user) {
        TimerSettings timerSettings = null;

        if (user != null) {
            timerSettings = user.getTimerSettings();
        }

        if (timerSettings != null)
            return new TimerStatusModel(timerSettings);

        return new TimerStatusModel(defaultTimerSettings);
    }
}
