package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.common.error.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/activities")
public class ActivityController {
    @Autowired
    private final ActivityService activityService;

    @GetMapping("/{task}/time/work")
    public ResponseEntity<Long> getTimeSpentInWork(@PathVariable Task task) {
        try {
            long timeSpentInWork = activityService.getTimeSpentInWork(task);
            return ResponseEntity.ok(timeSpentInWork);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{task}/time/testing")
    public ResponseEntity<Long> getTimeSpentInTesting(@PathVariable Task task) {
        try {
            long timeSpentInTesting = activityService.getTimeSpentInTesting(task);
            return ResponseEntity.ok(timeSpentInTesting);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
