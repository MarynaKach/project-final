package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.TaskTo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BacklogService {
    @Autowired
    private TaskService taskService;

    public List<TaskTo> getBacklogTasks() {
        List<TaskTo> tasks = taskService.getAll();
        List<TaskTo> taskMap = tasks.stream()
                .filter(task -> task.getSprint() == null)
                .collect(Collectors.toList());
        return taskMap;
    }
}
