package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.to.ProjectTo;
import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.javarush.jira.bugtracking.BugTrackingTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class BacklogServiceTest extends AbstractControllerTest {
    @Mock
    private TaskService taskService;

    @InjectMocks
    private BacklogService backlogService;

    @Test
    public void testGetBacklogTasks() {
        List<TaskTo> tasks = getTaskList();
        Mockito.when(taskService.getAll()).thenReturn(tasks);
        List<TaskTo> backlogTasks = backlogService.getBacklogTasks();
        assertEquals(1, backlogTasks.size());
    }

    @Test
    public void testGetBacklogTasksEmpty() {
        List<TaskTo> tasks = new ArrayList<>();
        Mockito.when(taskService.getAll()).thenReturn(tasks);
        List<TaskTo> backlogTasks = backlogService.getBacklogTasks();
        assertTrue(backlogTasks.isEmpty());
    }
}
