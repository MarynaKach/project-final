package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Activity;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.common.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    @Autowired
    private TaskRepository taskRepository;

    // TODO add the edges cases
    @GetMapping("/api/timeSpentInWork/{taskId}")
    public long getTimeSpentInWork(Task task) {
        Long id = task.getId();
        Timestamp inProgress = taskRepository.getUpdateTimeByStatus(id, "in progress");
        Timestamp ready = taskRepository.getUpdateTimeByStatus(id, "ready");
        if(inProgress != null && ready != null){
            return ready.getTime() - inProgress.getTime();
        }  else {
            throw new NotFoundException("No task with such " +  " found");
        }
    }
    // TODO add the edges cases
    @GetMapping("/api/timeSpentInTesting/{taskId}")
    public long getTimeSpentInTesting(Task task) {
        Long id = task.getId();
        Timestamp ready = taskRepository.getUpdateTimeByStatus(id, "ready");
        Timestamp done = taskRepository.getUpdateTimeByStatus(id, "done");
        if(done != null && ready != null){
            return done.getTime() - ready.getTime();
        }  else {
            throw new NotFoundException("No task with such " +  " found");
        }
    }
    /* tried to implement tag adding in different way
    @PutMapping("/tasks/{taskId}")
    public Task saveTags(@PathVariable Long taskId,
                           @RequestBody TaskTo taskTo,
                           @RequestParam(value = "tags", required = false) String tag) {
        //Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        Task task = mapper.toEntity(taskTo);
        task.getTags().add(tag);
        return taskRepository.save(task);
    }
    */

}
