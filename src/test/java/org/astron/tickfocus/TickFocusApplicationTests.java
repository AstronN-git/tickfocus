package org.astron.tickfocus;

import org.astron.tickfocus.controller.WorkspaceController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TickFocusApplicationTests {
    @Autowired
    WorkspaceController workspaceController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(workspaceController);
    }

}
