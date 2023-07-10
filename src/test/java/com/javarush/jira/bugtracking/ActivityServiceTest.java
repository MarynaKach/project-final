package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.ActivityRepository;
import com.javarush.jira.common.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest extends AbstractControllerTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    public void getTimeSpentInWorkWhenTaskExists() {
        Task task = new Task();
        task.setId(1L);
        Timestamp inProgressTimestamp = Timestamp.valueOf("2023-06-01 10:00:00");
        Timestamp readyTimestamp = Timestamp.valueOf("2023-06-01 12:00:00");
        when(activityRepository.getUpdateTimeByStatus(1L, "in progress")).thenReturn(inProgressTimestamp);
        when(activityRepository.getUpdateTimeByStatus(1L, "ready")).thenReturn(readyTimestamp);
        long expectedTimeSpent = readyTimestamp.getTime() - inProgressTimestamp.getTime();
        long actualTimeSpent = activityService.getTimeSpentInWork(task);
        assertEquals(expectedTimeSpent, actualTimeSpent);
        verify(activityRepository, times(1)).getUpdateTimeByStatus(1L, "in progress");
        verify(activityRepository, times(1)).getUpdateTimeByStatus(1L, "ready");
    }

    @Test
    public void getTimeSpentInWorkWhenTaskDoesNotExist() {
        Task task = new Task();
        task.setId(1L);
        when(activityRepository.getUpdateTimeByStatus(1L, "in progress")).thenReturn(null);
        when(activityRepository.getUpdateTimeByStatus(1L, "ready")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> activityService.getTimeSpentInWork(task));
        verify(activityRepository, times(1)).getUpdateTimeByStatus(1L, "in progress");
        verify(activityRepository, times(1)).getUpdateTimeByStatus(1L, "ready");
    }

    @Test
    public void getTimeSpentInTestingWhenTaskExists() {
        Task task = new Task();
        task.setId(1L);
        Timestamp readyTimestamp = Timestamp.valueOf("2023-06-01 12:00:00");
        Timestamp doneTimestamp = Timestamp.valueOf("2023-06-01 14:00:00");
        when(activityRepository.getUpdateTimeByStatus(1L, "ready")).thenReturn(readyTimestamp);
        when(activityRepository.getUpdateTimeByStatus(1L, "done")).thenReturn(doneTimestamp);
        long expectedTimeSpent = doneTimestamp.getTime() - readyTimestamp.getTime();
        long actualTimeSpent = activityService.getTimeSpentInTesting(task);
        assertEquals(expectedTimeSpent, actualTimeSpent);
    }

    @Test
    void getTimeSpentInTestingWhenTaskNotFound() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(activityRepository.getUpdateTimeByStatus(taskId, "ready")).thenReturn(null);
        when(activityRepository.getUpdateTimeByStatus(taskId, "done")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> activityService.getTimeSpentInTesting(task));
        verify(activityRepository, times(1)).getUpdateTimeByStatus(taskId, "ready");
        verify(activityRepository, times(1)).getUpdateTimeByStatus(taskId, "done");
    }
}
