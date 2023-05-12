package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.common.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Service
@RestController
@RequestMapping("/api")
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }
    @PutMapping
    public void saveTaskTag(Long taskId, String tag){
        if(repository.existsById(taskId))
            repository.saveTaskTag(taskId, tag);
        else
            throw new NotFoundException("No task with such "+taskId + " found");
    }
    @DeleteMapping
    public void deleteTaskTag(Long taskId){
        if(repository.existsById(taskId))
            repository.deleteTagByTaskId(taskId);
        else
            throw new NotFoundException("No task with such "+taskId + " found");
    }
}
