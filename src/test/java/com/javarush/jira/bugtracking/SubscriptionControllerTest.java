package com.javarush.jira.bugtracking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest extends AbstractControllerTest {

    @MockBean
    private SubscriptionService subscriptionService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void subscribeToTaskWhenNotSubscribed() throws Exception {
        Long taskId = 5L;
        Long userId = 1L;
        mockMvc.perform(post("/api/subscriptions/subscribe")
                        .param("taskId", taskId.toString())
                        .param("userId", userId.toString())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string("Subscribed to task successfully"));
    }

    @Test
    public void subscribeToTaskWhenTaskNotFound() throws Exception {
        Long taskId = 5L;
        Long userId = 3L;
        String errorMessage = "Task not found";
        doThrow(new NotFoundException(errorMessage))
                .when(subscriptionService).subscribeToTask(taskId, userId);
        mockMvc.perform(post("/api/subscriptions/subscribe")
                        .param("taskId", taskId.toString())
                        .param("userId", userId.toString())
                        .with(SecurityMockMvcRequestPostProcessors.user("user@gmail.com").password("user").roles("USER")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
        verify(subscriptionService, times(1)).subscribeToTask(taskId, userId);
    }


    @Test
    public void getAllTaskSubscribed() throws Exception {
        Long userId = 3L;
        List<String> tasksSubscribed = Arrays.asList("Task 1", "Task 2");
        when(subscriptionService.getAllTaskSubscribed(userId)).thenReturn(tasksSubscribed);
        mockMvc.perform(get("/api/subscriptions/tasks")
                        .param("userId", userId.toString())
                        .with(SecurityMockMvcRequestPostProcessors.user("user@gmail.com").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tasksSubscribed)));
        verify(subscriptionService, times(1)).getAllTaskSubscribed(userId);
    }


    @Test
    public void unsubscribeFromTaskWhenTaskFound() throws Exception {
        Long taskId = 5L;
        Long userId = 3L;
        doNothing().when(subscriptionService).unsubscribe(taskId, userId);
        mockMvc.perform(post("/api/subscriptions/unsubscribe")
                        .param("taskId", taskId.toString())
                        .param("userId", userId.toString())
                        .with(SecurityMockMvcRequestPostProcessors.user("user@gmail.com").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("Unsubscribed from task successfully"));
        verify(subscriptionService, times(1)).unsubscribe(taskId, userId);
    }


    @Test
    public void unsubscribeFromTaskWhenTaskNotFound() throws Exception {
        Long taskId = 5L;
        Long userId = 3L;
        String errorMessage = "Task not found";
        doThrow(new NotFoundException(errorMessage)).when(subscriptionService).unsubscribe(taskId, userId);
        mockMvc.perform(post("/api/subscriptions/unsubscribe")
                        .param("taskId", taskId.toString())
                        .param("userId", userId.toString())
                        .with(SecurityMockMvcRequestPostProcessors.user("user@gmail.com").password("user").roles("USER")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
        verify(subscriptionService, times(1)).unsubscribe(taskId, userId);
    }
}
