package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.common.error.NotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ActivityControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @Test
    void getTimeSpentInWorkWhenTaskExists() throws Exception {
        Long taskId = 2L;
        Task task = new Task();
        task.setId(taskId);
        long timeSpentInWork = 3600000;
        when(activityService.getTimeSpentInWork(task)).thenReturn(timeSpentInWork);
        mockMvc.perform(get("/api/activities/{task}/time/work", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(timeSpentInWork)));
        verify(activityService, times(1)).getTimeSpentInWork(task);
    }


    @Test
    void getTimeSpentInWorkWhenTaskNotFound() throws Exception {
        Long taskId = 5L;
        when(activityService.getTimeSpentInWork(Mockito.any(Task.class))).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/api/activities/{task}/time/work", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());
        verify(activityService, times(1)).getTimeSpentInWork(Mockito.any(Task.class));
    }

    @Test
    void getTimeSpentInTestingWhenTaskExists() throws Exception {
        Long taskId = 2L;
        Task task = new Task();
        task.setId(taskId);
        long timeSpentInTesting = 7200000;
        when(activityService.getTimeSpentInTesting(task)).thenReturn(timeSpentInTesting);
        mockMvc.perform(get("/api/activities/{task}/time/testing", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(timeSpentInTesting)));
        verify(activityService, times(1)).getTimeSpentInTesting(task);
    }

    @Test
    void getTimeSpentInTestingWhenTaskNotFound() throws Exception {
        Long taskId = 5L;
        when(activityService.getTimeSpentInTesting(any(Task.class))).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/api/activities/{task}/time/testing", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());
        verify(activityService, times(1)).getTimeSpentInTesting(any(Task.class));
    }
}
