package org.astron.tickfocus;

import org.astron.tickfocus.controller.WorkspaceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkspaceController.class)
public class WorkspaceControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testReturnsWorkspaceView() throws Exception {
        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk())
                .andExpect(view().name("workspace"));
    }

    @Test
    void testModelContainsIsTimerStartedAttribute() throws Exception {
        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("isTimerStarted"))
                .andExpect(model().attribute("isTimerStarted", false));
    }
}
