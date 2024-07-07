package org.astron.tickfocus.controller;

import org.astron.tickfocus.configuration.SecurityConfiguration;
import org.astron.tickfocus.configuration.TimerProperties;
import org.astron.tickfocus.model.TimerStateModel;
import org.astron.tickfocus.repository.TimerStateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkspaceController.class)
@Import(SecurityConfiguration.class)
public class WorkspaceControllerNonAuthenticatedTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TimerProperties timerProperties;

    @MockBean
    TimerStateRepository timerStateRepository;

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
        LocalDateTime localDateTime = LocalDateTime.now();

        mockMvc.perform(get("/workspace/startTimer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workspace"))
                .andExpect(request().sessionAttribute("timerState", allOf(
                        hasProperty("isTimerStarted", is(true)),
                        hasProperty("startDate", allOf(
                                notNullValue(),
                                greaterThanOrEqualTo(localDateTime)
                        ))
                )));
    }

    @Test
    void testTimerIsStoppedWhenVisitingTimerStop() throws Exception {
        TimerStateModel timerStateModel = new TimerStateModel();
        timerStateModel.setIsTimerStarted(true);
        timerStateModel.setStartDate(LocalDateTime.now());

        mockMvc.perform(get("/workspace/endTimer")
                .sessionAttr("timerState", timerStateModel))
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