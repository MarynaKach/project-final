package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.common.error.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class TaskServiceTest extends AbstractControllerTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void saveTaskTagTaskExist() {
        Long taskId = 1L;
        String tag = "exist";
        Mockito.when(repository.existsById(taskId)).thenReturn(true);
        taskService.saveTaskTag(taskId, tag);
        Mockito.verify(repository).saveTaskTag(taskId, tag);
    }

    @Test
    public void saveTaskTagNoExistingTask() {
        Long taskId = 2L;
        String tag = "doesn't exist";
        Mockito.when(repository.existsById(taskId)).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> {
            taskService.saveTaskTag(taskId, tag);
        });
    }

    @Test
    public void deleteTaskTagTaskExist() {
        Long taskId = 2L;
        Mockito.when(repository.existsById(taskId)).thenReturn(true);
        taskService.deleteTaskTag(taskId);
        Mockito.verify(repository).deleteTagByTaskId(taskId);
    }

    @Test
    public void deleteTaskTagNoExistingTask() {
        Long taskId = 2L;
        Mockito.when(repository.existsById(taskId)).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> {
            taskService.deleteTaskTag(taskId);
        });
    }
}
