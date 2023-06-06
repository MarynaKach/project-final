package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.ProjectTo;
import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.bugtracking.to.TaskTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BugTrackingTestData {
    public static TaskTo createTask1() {
        ProjectTo project = new ProjectTo(2l, "Project 1", true, null,
            null,null, null);
        TaskTo task1 = new TaskTo(1L, "Task 1", true, "typeCode", "statusCode", "Description",
                null, project, LocalDateTime.now(), "priorityCode",
                10, 5, null, null, null);
        return task1;
    }

    public static TaskTo createTask2() {
        ProjectTo project = new ProjectTo(2l, "Project 1", true, null,
                null,null, null);
        SprintTo sprint = new SprintTo(1l, "sprint 1", true, null,
                null, null, null);
        TaskTo task2 = new TaskTo(1L, "Task 1", true, "typeCode", "statusCode", "Description",
                sprint , project, LocalDateTime.now(), "priorityCode",
                10, 5, null, null, null);
        return task2;
    }

    public static List<TaskTo> getTaskList(){
        List<TaskTo> tasks = new ArrayList<>();
        TaskTo task1 = createTask1();
        TaskTo task2 = createTask2();
        tasks.add(task1);
        tasks.add(task2);
        return tasks;
    }

}
