package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskControllerTest extends AbstractControllerTest {

    @Mock
    private TaskService taskService;

    @Test
    public void saveTaskTagExistTask() {
        TaskController taskController = new TaskController(taskService);
        Long taskId = 1L;
        String tag = "exist";
        Mockito.doNothing().when(taskService).saveTaskTag(taskId, tag);
        ResponseEntity<String> response = taskController.saveTaskTag(taskId, tag);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task tag saved successfully", response.getBody());
    }

    @Test
    public void saveTaskTagNoExistingTask() {
        TaskController taskController = new TaskController(taskService);
        Long taskId = 1L;
        String tag = "no existing";
        Mockito.doThrow(NotFoundException.class).when(taskService).saveTaskTag(taskId, tag);
        ResponseEntity<String> response = taskController.saveTaskTag(taskId, tag);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No task with ID " + taskId + " found", response.getBody());
    }

    @Test
    public void saveTaskTagInternalServerError() {
        TaskController taskController = new TaskController(taskService);
        Long taskId = 1L;
        String tag = "error";
        Mockito.doThrow(RuntimeException.class).when(taskService).saveTaskTag(taskId, tag);
        ResponseEntity<String> response = taskController.saveTaskTag(taskId, tag);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error saving task tag", response.getBody());
    }

    @Test
    public void deleteTaskTagOkResponse() {
        TaskController taskController = new TaskController(taskService);
        Long taskId = 1L;
        Mockito.doNothing().when(taskService).deleteTaskTag(taskId);
        ResponseEntity<String> response = taskController.deleteTaskTag(taskId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task tag deleted successfully", response.getBody());
    }
}
