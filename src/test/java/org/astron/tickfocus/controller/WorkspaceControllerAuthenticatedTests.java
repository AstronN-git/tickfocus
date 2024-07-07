package org.astron.tickfocus.controller;

import org.astron.tickfocus.TestUserFactory;
import org.astron.tickfocus.configuration.SecurityConfiguration;
import org.astron.tickfocus.configuration.TimerProperties;
import org.astron.tickfocus.entity.TimerState;
import org.astron.tickfocus.repository.TimerStateRepository;
import org.astron.tickfocus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@MockBeans({@MockBean(UserService.class), @MockBean(TimerProperties.class), @MockBean(TimerStateRepository.class)})
@Import(SecurityConfiguration.class)
public class WorkspaceControllerAuthenticatedTests {
    private MockMvc mockMvc;
    @Autowired
    private TimerProperties timerProperties;
    @Autowired
    private TimerStateRepository timerStateRepository;
    @Autowired
    private ISpringTemplateEngine templateEngine;

    @BeforeEach
    void setUpMockMvc() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new WorkspaceController(timerProperties, timerStateRepository))
                .setCustomArgumentResolvers(new AuthenticationArgumentResolver())
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testTimerIsStartedBasedOnDatabase() throws Exception {
        TimerState timerState = new TimerState();
        timerState.setTimerStarted(LocalDateTime.now());
        when(timerStateRepository.findByUser(TestUserFactory.createTestUser()))
                .thenReturn(timerState);

        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("timerState", allOf(
                        hasProperty("isTimerStarted", is(true)),
                        hasProperty("startDate", is(timerState.getTimerStarted()))
                )));
    }

    @Test
    void testTimerIsNotStartedBasedOnDatabase() throws Exception {
        TimerState timerState = new TimerState();
        when(timerStateRepository.findByUser(TestUserFactory.createTestUser()))
                .thenReturn(timerState);

        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("timerState",
                        hasProperty("isTimerStarted", is(false))
                ));
    }

    @Test
    void testTimerInDatabaseStoppedOnTimerStop() throws Exception {
        mockMvc.perform(get("/workspace/endTimer"))
                .andExpect(status().is3xxRedirection());

        verify(timerStateRepository, times(1)).updateTimerStartedByUser(isNull(), eq(TestUserFactory.createTestUser()));
    }

    @Test
    void testTimerInDatabaseStartedOnTimerStart() throws Exception {
        mockMvc.perform(get("/workspace/startTimer"))
                .andExpect(status().is3xxRedirection());

        verify(timerStateRepository, times(1)).updateTimerStartedByUser(notNull(), eq(TestUserFactory.createTestUser()));
    }
}
