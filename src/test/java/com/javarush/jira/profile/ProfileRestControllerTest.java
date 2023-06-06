package com.javarush.jira.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.User;
import com.javarush.jira.login.internal.UserMapper;
import com.javarush.jira.login.internal.UserRepository;
import com.javarush.jira.profile.web.ProfileRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.profile.ProfileTestData.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository repository;
    @Autowired
    UserMapper mapper;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void testUpdateProfile() throws Exception {
        User dbUserBefore = repository.getExistedByEmail(ADMIN_MAIL);
        ProfileTo updatedProfileTo = createProfileTo();
        mockMvc.perform(put(ProfileRestController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProfileTo)))
                .andExpect(status().isNoContent())
                .andReturn();
        User dbUserAfter = repository.getExistedByEmail(ADMIN_MAIL);
        assertEquals(dbUserBefore.getPassword(), dbUserAfter.getPassword(), "user's password must not be changed");
    }
}
