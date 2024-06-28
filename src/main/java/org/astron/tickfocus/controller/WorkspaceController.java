package org.astron.tickfocus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workspace")
public class WorkspaceController {
    @GetMapping
    public String showHomeView() {
        return "workspace";
    }
}
