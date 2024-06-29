package org.astron.tickfocus;

import org.astron.tickfocus.configuration.TimerProperties;
import org.astron.tickfocus.controller.WorkspaceController;
import org.astron.tickfocus.model.TimerState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkspaceController.class)
public class WorkspaceControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TimerProperties timerProperties;

    @Test
    void testReturnsWorkspaceView() throws Exception {
        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk())
                .andExpect(view().name("workspace"));
    }

    @Test
    void testModelContainsNeededAttributes() throws Exception {
        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("timerState"))
                .andExpect(request().sessionAttribute("timerState", allOf(
                        hasProperty("isTimerStarted", is(false)),
                        hasProperty("startDate", nullValue())
                )));
    }

    @Test
    void testTimerIsStartedWhenVisitingTimerStart() throws Exception {
        Date date = new Date();

        mockMvc.perform(get("/workspace/startTimer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workspace"))
                .andExpect(request().sessionAttribute("timerState", allOf(
                        hasProperty("isTimerStarted", is(true)),
                        hasProperty("startDate", allOf(
                                notNullValue(),
                                greaterThanOrEqualTo(date)
                        ))
                )));
    }

    @Test
    void testTimerIsStoppedWhenVisitingTimerStop() throws Exception {
        TimerState timerState = new TimerState();
        timerState.setIsTimerStarted(true);
        timerState.setStartDate(new Date());

        mockMvc.perform(get("/workspace/endTimer")
                .sessionAttr("timerState", timerState))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workspace"))
                .andExpect(request().sessionAttribute("timerState", allOf(
                        hasProperty("isTimerStarted", is(false)),
                        hasProperty("startDate", nullValue())
                )));
    }

    @Test
    void testTimerPropertiesIsInModel() throws Exception {
        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(
                        "timerProperties", allOf(notNullValue(), is(timerProperties))
                ));
    }
}
