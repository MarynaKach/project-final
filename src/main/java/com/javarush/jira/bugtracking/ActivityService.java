package com.javarush.jira.bugtracking;


import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.ActivityRepository;
import com.javarush.jira.common.error.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public long getTimeSpentInWork(Task task) {
        Long id = task.getId();
        Timestamp inProgress = activityRepository.getUpdateTimeByStatus(id, "in progress");
        Timestamp ready = activityRepository.getUpdateTimeByStatus(id, "ready");
        if (inProgress != null && ready != null) {
            return ready.getTime() - inProgress.getTime();
        } else {
            throw new NotFoundException("No task with such " + " found");
        }
    }

    public long getTimeSpentInTesting(Task task) {
        Long id = task.getId();
        Timestamp ready = activityRepository.getUpdateTimeByStatus(id, "ready");
        Timestamp done = activityRepository.getUpdateTimeByStatus(id, "done");
        if (done != null && ready != null) {
            return done.getTime() - ready.getTime();
        } else {
            throw new NotFoundException("No task with such " + " found");
        }
    }
}
