package com.javarush.jira.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.web.ProfileRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.profile.ProfileTestData.ADMIN_MAIL;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    public void testUpdateProfile() throws Exception {
        AuthUser authUser = new AuthUser(ProfileTestData.createUser());
        mockMvc.perform(put(ProfileRestController.REST_URL)
                        .with(user(authUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProfileTestData.createProfileTo())))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
