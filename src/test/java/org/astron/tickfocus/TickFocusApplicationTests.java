package org.astron.tickfocus;

import org.astron.tickfocus.configuration.TimerProperties;
import org.astron.tickfocus.controller.AuthenticationController;
import org.astron.tickfocus.controller.WorkspaceController;
import org.astron.tickfocus.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled("test is too long; enable while building for production")
class TickFocusApplicationTests {
    @Autowired
    WorkspaceController workspaceController;

    @Autowired
    TimerProperties timerProperties;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationController authenticationController;

    @Test
    void contextLoads() {
        assertNotNull(workspaceController);
        assertNotNull(timerProperties);
        assertNotNull(authenticationController);
        assertInstanceOf(UserService.class, userDetailsService);
    }

}
