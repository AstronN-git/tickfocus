package org.astron.tickfocus;

import org.astron.tickfocus.configuration.TimerProperties;
import org.astron.tickfocus.controller.WorkspaceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TickFocusApplicationTests {
    @Autowired
    WorkspaceController workspaceController;

    @Autowired
    TimerProperties timerProperties;

    @Test
    void contextLoads() {
        assertNotNull(workspaceController);
        assertNotNull(timerProperties);
    }

}
