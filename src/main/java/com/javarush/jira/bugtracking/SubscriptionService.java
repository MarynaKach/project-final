package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.common.error.AlreadySubscribedException;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.internal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Component
public class SubscriptionService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public void subscribeToTask(Long taskId, Long userId) {
        if(checkIfSubscribed(taskId, userId)) {
            throw new AlreadySubscribedException("User with id "+userId + " has already subscribed to the task with id " + taskId);
        }
        if(userRepository.existsById(userId) && taskRepository.existsById(taskId)) {
            userRepository.subscribeToTask(taskId, userId);
        }
        else
            throw new NotFoundException("No task or user with such "+taskId + " found");
    }

    public List<String> getAllTaskSubscribed (@RequestParam Long userId) {
        List<String> tasksSubscribed = new ArrayList<>(userRepository.getAllSubscriptions(userId));
        return tasksSubscribed;
    }

    public void unsubscribe(Long taskId, Long userId) {
        if(userRepository.existsById(userId) && taskRepository.existsById(taskId)) {
            userRepository.unsubscribeFromTask(taskId, userId);
        }
        else
            throw new NotFoundException("No task or user with such "+taskId + " found");
    }
    public boolean checkIfSubscribed (Long taskId, Long userId) {
        return userRepository.checkIfSubscribed(taskId, userId) > 0;
    }
}
