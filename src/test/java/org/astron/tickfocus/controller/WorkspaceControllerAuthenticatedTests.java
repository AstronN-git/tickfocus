package org.astron.tickfocus.controller;

import org.astron.tickfocus.configuration.SecurityConfiguration;
import org.astron.tickfocus.configuration.TimerProperties;
import org.astron.tickfocus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@WebMvcTest
@MockBeans({@MockBean(UserService.class), @MockBean(TimerProperties.class)})
@Import(SecurityConfiguration.class)
public class WorkspaceControllerAuthenticatedTests {
    private MockMvc mockMvc;
    @Autowired
    private TimerProperties timerProperties;
    @Autowired
    private ISpringTemplateEngine templateEngine;

    @BeforeEach
    void setUpMockMvc() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new WorkspaceController(timerProperties))
                .setCustomArgumentResolvers(new AuthenticationArgumentResolver())
                .setViewResolvers(viewResolver)
                .build();
    }
}
