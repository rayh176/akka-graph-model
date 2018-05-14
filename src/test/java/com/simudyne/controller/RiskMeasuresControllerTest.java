package com.simudyne.controller;

import com.simudyne.config.AppConfigTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@WebMvcTest(RiskMeasuresController.class)
public class RiskMeasuresControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @MockBean
    //@Autowired
    private RiskMeasuresController mockRiskMeasuresController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(webApplicationContext).build();
    }

    @Test
    public void testRiskMeasuresController() throws Exception {
        assertThat(this.mockRiskMeasuresController, is(notNullValue()));
    }


    @Test
    public void testRegisterRiskMeasure() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(
                post("/risk-measures/1.3")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

        // then
        assertThat(response, is(notNullValue()));

    }

}
