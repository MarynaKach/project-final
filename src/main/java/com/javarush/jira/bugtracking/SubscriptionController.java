package com.javarush.jira.bugtracking;

import com.javarush.jira.common.error.AlreadySubscribedException;
import com.javarush.jira.common.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    @Autowired
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToTask(@RequestParam Long taskId, @RequestParam Long userId) {
        try {
            subscriptionService.subscribeToTask(taskId, userId);
            return ResponseEntity.ok("Subscribed to task successfully");
        } catch (AlreadySubscribedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<String>> getAllTaskSubscribed(@RequestParam Long userId) {
        List<String> tasksSubscribed = subscriptionService.getAllTaskSubscribed(userId);
        return ResponseEntity.ok(tasksSubscribed);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeFromTask(@RequestParam Long taskId, @RequestParam Long userId) {
        try {
            subscriptionService.unsubscribe(taskId, userId);
            return ResponseEntity.ok("Unsubscribed from task successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
