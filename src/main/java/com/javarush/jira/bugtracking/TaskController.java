package com.javarush.jira.bugtracking;

import com.javarush.jira.common.error.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PutMapping("/{taskId}/saveTag")
    public ResponseEntity<String> saveTaskTag(@PathVariable Long taskId, @RequestBody String tag) {
        try {
            taskService.saveTaskTag(taskId, tag);
            return ResponseEntity.ok("Task tag saved successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No task with ID " + taskId + " found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving task tag");
        }
    }

    @DeleteMapping("/{taskId}/deleteTag")
    public ResponseEntity<String> deleteTaskTag(@PathVariable Long taskId) {
        try {
            taskService.deleteTaskTag(taskId);
            return ResponseEntity.ok("Task tag deleted successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No task with ID " + taskId + " found");
        }
    }
}
