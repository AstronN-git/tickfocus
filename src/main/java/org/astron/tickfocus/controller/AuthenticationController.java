package org.astron.tickfocus.controller;

import jakarta.validation.Valid;
import org.astron.tickfocus.entity.User;
import org.astron.tickfocus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginView() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationView(@ModelAttribute(name = "userObject") User user) {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @ModelAttribute(name = "userObject") @Valid User user,
            Errors errors
    ) {
        userService.validateUniqueFields(user, errors);

        if (errors.hasErrors()) {
            return "register";
        }

        User savedUser = userService.registerUser(user);
        log.info("User registered: {}", savedUser);

        return "redirect:/login";
    }
}
