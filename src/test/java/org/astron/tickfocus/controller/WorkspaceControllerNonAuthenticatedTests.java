package org.astron.tickfocus.controller;

import org.astron.tickfocus.configuration.DefaultTimerSettings;
import org.astron.tickfocus.configuration.SecurityConfiguration;
import org.astron.tickfocus.configuration.TimerSettings;
import org.astron.tickfocus.model.TimerStatusModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkspaceController.class)
@Import(SecurityConfiguration.class)
public class WorkspaceControllerNonAuthenticatedTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean(DefaultTimerSettings.class)
    TimerSettings timerSettings;

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
                .andExpect(model().attributeExists("timerStatus"))
                .andExpect(request().sessionAttribute("timerStatus", allOf(
                        hasProperty("running", is(false)),
                        hasProperty("startDate", nullValue())
                )));
    }

    @Test
    void testTimerIsStartedWhenVisitingTimerStart() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);

        when(timerSettings.getWorkingTime())
                .thenReturn(1500000);

        mockMvc.perform(get("/workspace/startTimer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workspace"))
                .andExpect(request().sessionAttribute("timerStatus", allOf(
                        hasProperty("running", is(true)),
                        hasProperty("startDate", allOf(
                                notNullValue(),
                                greaterThanOrEqualTo(localDateTime)
                        )),
                        hasProperty("text", is("Working...")),
                        hasProperty("primary", is(true)),
                        hasProperty("endDate", greaterThan(LocalDateTime.now(ZoneOffset.UTC)))
                )));
    }

    @Test
    void testTimerIsStoppedWhenVisitingTimerStop() throws Exception {
        TimerStatusModel timerStatusModel = new TimerStatusModel(timerSettings);
        timerStatusModel.start();

        mockMvc.perform(get("/workspace/stopTimer")
                .sessionAttr("timerStatus", timerStatusModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workspace"))
                .andExpect(request().sessionAttribute("timerStatus", allOf(
                        hasProperty("running", is(false)),
                        hasProperty("startDate", nullValue())
                )));
    }

    @Test
    void testTimerIsSwitchedToRestingWhenVisitingTimerEnd() throws Exception {
        TimerStatusModel timerStatusModel = new TimerStatusModel(timerSettings);
        timerStatusModel.start();

        when(timerSettings.getRestingTime())
                .thenReturn(300000);

        mockMvc.perform(get("/workspace/endTimer")
                .sessionAttr("timerStatus", timerStatusModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workspace"))
                .andExpect(request().sessionAttribute("timerStatus", allOf(
                        hasProperty("running", is(true)),
                        hasProperty("timerState", is(timerStatusModel.getRestingState())),
                        hasProperty("startDate", lessThanOrEqualTo(LocalDateTime.now(ZoneOffset.UTC))),
                        hasProperty("text", is("Resting...")),
                        hasProperty("primary", is(false)),
                        hasProperty("endDate", greaterThan(LocalDateTime.now(ZoneOffset.UTC)))
                )));
    }
}
