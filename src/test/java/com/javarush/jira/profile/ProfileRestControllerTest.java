package com.javarush.jira.profile;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.profile.web.ProfileRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

public class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "user@gmail.com")
    void get_ReturnsProfileTo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Autowired
    private TaskRepository taskRepository;


    /*@Test
    @WithUserDetails(value = "admin@example.com")
    void update_ReturnsNoContent() throws Exception {
        ProfileTo profileTo = new ProfileTo();
        profileTo.setFirstName("John");
        profileTo.setLastName("Doe");

        mockMvc.perform(put("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeValue(profileTo)))
                .andExpect(status().isNoContent());
    }*/
}
