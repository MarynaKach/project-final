package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.common.error.AlreadySubscribedException;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.internal.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionServiceTest extends AbstractControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    private SubscriptionService subscriptionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        subscriptionService = new SubscriptionService(userRepository, taskRepository);
    }

    @Test
    public void subscribeToTaskWhenUserNotSubscribed() {
        Long taskId = 5L;
        Long userId = 3L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(true);
        subscriptionService.subscribeToTask(taskId, userId);
        Mockito.verify(userRepository).subscribeToTask(taskId, userId);
    }

    @Test
    public void subscribeToTaskWhenAlreadySubscribed() {
        Long taskId = 1L;
        Long userId = 1L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(true);
        Mockito.when(userRepository.checkIfSubscribed(taskId, userId)).thenReturn(3L);
        assertThrows(AlreadySubscribedException.class, () -> subscriptionService.subscribeToTask(taskId, userId));
    }

    @Test
    public void subscribeToTaskWhenTaskOrUserNotFound() {
        Long taskId = 1L;
        Long userId = 1L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> subscriptionService.subscribeToTask(taskId, userId));
    }

    @Test
    public void getAllTaskSubscribed() {
        Long userId = 3L;
        List<String> expectedTasksSubscribed = Arrays.asList("Task 1", "Task 2", "Task 3");
        Mockito.when(userRepository.getAllSubscriptions(userId)).thenReturn(expectedTasksSubscribed);
        List<String> actualTasksSubscribed = subscriptionService.getAllTaskSubscribed(userId);
        assertEquals(expectedTasksSubscribed, actualTasksSubscribed);
    }

    @Test
    public void unsubscribeWhenSubscribed() {
        Long taskId = 3L;
        Long userId = 2L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(true);
        subscriptionService.unsubscribe(taskId, userId);
        Mockito.verify(userRepository).unsubscribeFromTask(taskId, userId);
    }

    @Test
    public void unsubscribeWhenTaskOrUserNotFound() {
        Long taskId = 1L;
        Long userId = 1L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> subscriptionService.unsubscribe(taskId, userId));
    }

    @Test
    public void checkIfSubscribedWhenSubscribed() {
        Long taskId = 1L;
        Long userId = 1L;
        Mockito.when(userRepository.checkIfSubscribed(taskId, userId)).thenReturn(1l);
        boolean isSubscribed = subscriptionService.checkIfSubscribed(taskId, userId);
        assertTrue(isSubscribed);
    }

    @Test
    public void checkIfSubscribedWhenNotSubscribed() {
        Long taskId = 2L;
        Long userId = 3L;
        Mockito.when(userRepository.checkIfSubscribed(taskId, userId)).thenReturn(0l);
        boolean isSubscribed = subscriptionService.checkIfSubscribed(taskId, userId);
        assertFalse(isSubscribed);
    }
}
