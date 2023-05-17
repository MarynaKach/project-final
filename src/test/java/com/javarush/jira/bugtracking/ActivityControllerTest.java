package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ActivityControllerTest extends AbstractControllerTest  {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;
    @MockBean
    private ActivityService activityService;

    @Test
    @WithMockUser
    void getTimeSpentInWork_ReturnsTimeSpent() throws Exception {
        Long taskId = 1L;
        long timeSpentInWork = 3600000L; // 1 hour
        Task task = new Task();
        task.setId(taskId);
        //when(taskService.getAll()).thenReturn((List<TaskTo>) task);
        when(activityService.getTimeSpentInWork(task)).thenReturn(timeSpentInWork);
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/activities/{task}/time/work", task))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$", is(timeSpentInWork)));
        verify(activityService).getTimeSpentInWork(task);
    }

    /*@Test
    void getTimeSpentInWork_ReturnsNotFound() throws Exception {
        // Arrange
        Long taskId = 1L;

        // Mock the taskService behavior to throw NotFoundException
        when(taskService.getTaskById(taskId)).thenThrow(new NotFoundException("No task with such " + taskId + " found"));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{taskId}/time/work", taskId))
                .andExpect(status().isNotFound());

        // Verify that taskService methods were called
        verify(taskService).getTaskById(taskId);
    }
*/
}