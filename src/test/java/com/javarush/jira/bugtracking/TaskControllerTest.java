package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.error.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends AbstractControllerTest {

    @MockBean
    private TaskService taskService;

    @Test
    public void saveTaskTagOkResponse() throws Exception {
        Long taskId = 2L;
        String tag = "exist";
        Mockito.doNothing().when(taskService).saveTaskTag(taskId, tag);
        mockMvc.perform(put("/api/tasks/{taskId}/saveTag", taskId)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(tag)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string("Task tag saved successfully"));
    }

    @Test
    public void saveTaskTagNotFoundResponse() throws Exception {
        Long taskId = 2L;
        String tag = "no exist";
        Mockito.doThrow(NotFoundException.class).when(taskService).saveTaskTag(taskId, tag);
        mockMvc.perform(put("/api/tasks/{taskId}/saveTag", taskId)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(tag)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No task with ID " + taskId + " found"));
    }

    @Test
    public void deleteTaskTagOkResponse() throws Exception {
        Long taskId = 2L;
        Mockito.doNothing().when(taskService).deleteTaskTag(taskId);
        mockMvc.perform(delete("/api/tasks/{taskId}/deleteTag", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string("Task tag deleted successfully"));
    }

    @Test
    public void deleteTaskTagNotFoundResponse() throws Exception {
        Long taskId = 2L;
        Mockito.doThrow(NotFoundException.class).when(taskService).deleteTaskTag(taskId);

        mockMvc.perform(delete("/api/tasks/{taskId}/deleteTag", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No task with ID " + taskId + " found"));
    }

    @Test
    public void saveTaskTagInternalServerError() throws Exception {
        Long taskId = 1L;
        String tag = "error";
        Mockito.doThrow(RuntimeException.class).when(taskService).saveTaskTag(taskId, tag);
        mockMvc.perform(put("/api/tasks/{taskId}/saveTag", taskId)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(tag)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error saving task tag"));
    }
}
