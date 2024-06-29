package org.astron.tickfocus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/workspace")
@SessionAttributes("isTimerStarted")
public class WorkspaceController {
    @GetMapping
    public String showHomeView() {
        return "workspace";
    }

    @ModelAttribute("isTimerStarted")
    private Boolean isTimerStarted() {
        return false;
    }
}
