package com.capco.assignment.alahunou;

import com.capco.assignment.alahunou.controller.FeatureController;
import com.capco.assignment.alahunou.service.UserService;
import com.capco.assignment.alahunou.view.FeatureDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AliakseiLahunouTestAssignmentAppTest {

    @Autowired
    private FeatureController featureController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void resetMocks() {
        Mockito.reset(userService);
    }

    @Test
    void contextLoads() {
        assertThat(featureController).isNotNull();
    }

    @Test
    public void whenUserBasicAuth_shouldPermit() throws Exception {
        long userId = new Random().nextLong();
        when(userService.getFeatures(anyLong())).thenCallRealMethod();

        mvc.perform(get("/user/" + userId + "/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("user","userPass")))
                .andExpect(status().is2xxSuccessful());

        verify(userService, times(1)).getFeatures(userId);
    }

    @Test
    public void whenUserBasicAuthWithWrongCreds_shouldForbid() throws Exception {
        long userId = new Random().nextLong();
        when(userService.getFeatures(anyLong())).thenCallRealMethod();

        mvc.perform(get("/user/" + userId + "/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("user","badCreds")))
                .andExpect(status().is4xxClientError());

        verify(userService, times(0)).getFeatures(userId);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void whenCreateFeatureAsUser_forbid() throws Exception {
        String featureName = "testFeature";
        FeatureDto requestBody = new FeatureDto(null, featureName, null);
        String bodyAsStr = objectMapper.writeValueAsString(requestBody);

        mvc.perform(post("/feature").contentType(MediaType.APPLICATION_JSON)
                        .content(bodyAsStr))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenCreateFeatureAsAdmin_expectSuccess() throws Exception {
        String featureName = "testFeature";
        FeatureDto requestBody = new FeatureDto(null, featureName, null);
        String bodyAsStr = objectMapper.writeValueAsString(requestBody);

        mvc.perform(post("/feature").contentType(MediaType.APPLICATION_JSON)
                        .content(bodyAsStr))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.disabled").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSwitchFeatureForUserAsAdmin_expectSuccess() throws Exception {
        long userId = new Random().nextLong();
        long featureId = new Random().nextLong();
        boolean featureState = false;
        FeatureDto requestBody = new FeatureDto(featureId, null, featureState);
        String bodyAsStr = objectMapper.writeValueAsString(requestBody);

        mvc.perform(put("/user/" + userId + "/feature").contentType(MediaType.APPLICATION_JSON)
                        .content(bodyAsStr))
                .andExpect(status().is2xxSuccessful());

        verify(userService, times(1)).switchFeature(userId, featureId, featureState);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void whenSwitchFeatureForUserAsUser_expectForbidden() throws Exception {
        long userId = new Random().nextLong();
        long featureId = new Random().nextLong();
        boolean featureState = false;
        FeatureDto requestBody = new FeatureDto(featureId, null, featureState);
        String bodyAsStr = objectMapper.writeValueAsString(requestBody);

        mvc.perform(put("/user/" + userId + "/feature").contentType(MediaType.APPLICATION_JSON)
                        .content(bodyAsStr))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(userService);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void whenGetAllFeaturesForUser_expectSuccess() throws Exception {
        long userId = new Random().nextLong();
        when(userService.getFeatures(anyLong())).thenCallRealMethod();

        mvc.perform(get("/user/" + userId + "/feature").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.allFeatures").exists())
                .andExpect(jsonPath("$.allFeatures").isArray())
                .andExpect(jsonPath("$.allFeatures[0]").exists())
                .andExpect(jsonPath("$.userFeatures").exists())
                .andExpect(jsonPath("$.userFeatures").isArray())
                .andExpect(jsonPath("$.userFeatures[0]").exists());

        verify(userService, times(1)).getFeatures(userId);
    }
}
