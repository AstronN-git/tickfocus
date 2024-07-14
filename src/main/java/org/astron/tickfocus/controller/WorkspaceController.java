package org.astron.tickfocus.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.astron.tickfocus.configuration.DefaultTimerSettings;
import org.astron.tickfocus.configuration.TimerSettings;
import org.astron.tickfocus.entity.User;
import org.astron.tickfocus.model.SimpleTimerSettings;
import org.astron.tickfocus.model.TimerStatusModel;
import org.astron.tickfocus.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@Controller
@RequestMapping("/workspace")
@SessionAttributes("timerStatus")
public class WorkspaceController {
    private final DefaultTimerSettings defaultTimerSettings;
    private final UserService userService;

    public WorkspaceController(DefaultTimerSettings defaultTimerSettings, UserService userService) {
        this.defaultTimerSettings = defaultTimerSettings;
        this.userService = userService;
    }

    @GetMapping
    public String showHomeView(
            @AuthenticationPrincipal User user,
            @ModelAttribute("timerStatus") TimerStatusModel timerStatusModel,
            Model model
    ) {
        if (!timerStatusUpToDate(timerStatusModel, user)) {
            model.addAttribute("timerStatus", timerStatus(user));
        }
        return "workspace";
    }

    private boolean timerStatusUpToDate(
            TimerStatusModel timerStatusModel,
            User user
    ) {
        if (user == null)
            return true;

        return timerStatusModel.getTimerSettings().equals(userService.findTimerSettingsByUser(user));
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

    @PostMapping("/settings")
    public String saveSettings(
            @AuthenticationPrincipal User user,
            @Valid SimpleTimerSettings timerSettings,
            Errors errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            return "redirect:/workspace?settings-set-err";
        }

        TimerSettings millisTimerSettings = getMillisTimerSettings(timerSettings);

        userService.updateTimerSettings(user, millisTimerSettings);

        model.addAttribute("timerStatus", timerStatus(user));

        return "redirect:/workspace";
    }

    @ModelAttribute("timerStatus")
    public TimerStatusModel timerStatus(@AuthenticationPrincipal User user) {
        TimerSettings timerSettings = null;
        log.info("creating TimerStatusModel with User {}", user);

        if (user != null) {
            timerSettings = userService.findTimerSettingsByUser(user);
        }

        if (timerSettings != null)
            return new TimerStatusModel(timerSettings);

        return new TimerStatusModel(defaultTimerSettings);
    }

    @ModelAttribute("timerSettings")
    public SimpleTimerSettings timerSettings() {
        return new SimpleTimerSettings();
    }

    private TimerSettings getMillisTimerSettings(TimerSettings secondsTimerSettings) {
        SimpleTimerSettings simpleTimerSettings = new SimpleTimerSettings();
        simpleTimerSettings.setWorkingTime(secondsTimerSettings.getWorkingTime() * 1000);
        simpleTimerSettings.setRestingTime(secondsTimerSettings.getRestingTime() * 1000);

        return simpleTimerSettings;
    }
}
