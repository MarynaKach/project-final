package com.javarush.jira.bugtracking.internal.mapper;

import com.javarush.jira.bugtracking.internal.model.Activity;
import com.javarush.jira.bugtracking.internal.model.Project;
import com.javarush.jira.bugtracking.internal.model.Sprint;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.to.ActivityTo;
import com.javarush.jira.bugtracking.to.ProjectTo;
import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Autowired
    private SprintMapper sprintMapper;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<Task> toEntityList(Collection<TaskTo> tos) {
        if (tos == null) {
            return null;
        }
        List<Task> list = new ArrayList<>(tos.size());
        for (TaskTo taskTo : tos) {
            list.add(toEntity(taskTo));
        }
        return list;
    }

    @Override
    public Task updateFromTo(Task entity, TaskTo to) {
        if (to == null) {
            return entity;
        }
        entity.setId(to.getId());
        entity.setEnabled(to.isEnabled());
        entity.setTitle(to.getTitle());
        entity.setTypeCode(to.getTypeCode());
        entity.setStatusCode(to.getStatusCode());
        entity.setPriorityCode(to.getPriorityCode());
        entity.setDescription(to.getDescription());
        if (to.getProject() != null) {
            if (entity.getProject() == null) {
                entity.setProject(new Project());
            }
            projectMapper.updateFromTo(entity.getProject(), to.getProject());
        } else {
            entity.setProject(null);
        }
        if (to.getSprint() != null) {
            if (entity.getSprint() == null) {
                entity.setSprint(new Sprint());
            }
            sprintMapper.updateFromTo(entity.getSprint(), to.getSprint());
        } else {
            entity.setSprint(null);
        }
        entity.setUpdated(to.getUpdated());
        entity.setEstimate(to.getEstimate());
        if (entity.getTags() != null) {
            Set<String> set = to.getTags();
            if (set != null) {
                entity.getTags().clear();
                entity.getTags().addAll(set);
            } else {
                entity.setTags(null);
            }
        } else {
            Set<String> set = to.getTags();
            if (set != null) {
                entity.setTags(new LinkedHashSet<>(set));
            }
        }
        if (entity.getActivities() != null) {
            List<Activity> list = activityToListToActivityList(to.getActivities());
            if (list != null) {
                entity.getActivities().clear();
                entity.getActivities().addAll(list);
            } else {
                entity.setActivities(null);
            }
        } else {
            List<Activity> list = activityToListToActivityList(to.getActivities());
            if (list != null) {
                entity.setActivities(list);
            }
        }
        entity.setParent(toEntity(to.getParent()));

        return entity;
    }

    @Override
    public TaskTo toTo(Task task) {
        if (task == null) {
            return null;
        }
        List<ActivityTo> activities = null;
        int estimate = 0;
        Long id = task.getId();
        String title = task.getTitle();
        Set<String> tags = task.getTags();
        if (tags != null) {
            tags = new LinkedHashSet<>(tags);
        }
        String typeCode = task.getTypeCode();
        String statusCode = task.getStatusCode();
        String description = task.getDescription();
        SprintTo sprint = sprintMapper.toTo(task.getSprint());
        ProjectTo project = projectMapper.toTo(task.getProject());
        LocalDateTime updated = task.getUpdated();
        String priorityCode = task.getPriorityCode();
        if (task.getEstimate() != null) {
            estimate = task.getEstimate();
        }
        TaskTo parent = taskToTaskTo(task.getParent());

        boolean enabled = task.isEnabled();
        int storyPoints = 0;
        TaskTo taskTo = new TaskTo(id, title, enabled, typeCode, statusCode, description, sprint, project, updated, priorityCode, estimate, storyPoints, tags, activities, parent);
        activities = activityListToActivityToList(task.getActivities(), taskTo);
        TaskTo updatedTaskTo = new TaskTo(id, title, enabled, typeCode, statusCode, description, sprint, project, updated, priorityCode, estimate, storyPoints, tags, activities, parent);
        return updatedTaskTo;
    }

    @Override
    public Task toEntity(TaskTo taskTo) {
        if (taskTo == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskTo.getId());
        task.setEnabled(taskTo.isEnabled());
        task.setTitle(taskTo.getTitle());
        task.setTypeCode(taskTo.getTypeCode());
        task.setStatusCode(taskTo.getStatusCode());
        task.setPriorityCode(taskTo.getPriorityCode());
        task.setDescription(taskTo.getDescription());
        task.setProject(projectMapper.toEntity(taskTo.getProject()));
        task.setSprint(sprintMapper.toEntity(taskTo.getSprint()));
        task.setUpdated(taskTo.getUpdated());
        task.setEstimate(taskTo.getEstimate());
        Set<String> set = taskTo.getTags();
        if (set != null) {
            task.setTags(new LinkedHashSet<>(set));
        }
        task.setActivities(activityToListToActivityList(taskTo.getActivities()));
        task.setParent(taskToToTask(taskTo.getParent()));

        return task;
    }

    @Override
    public List<TaskTo> toToList(Collection<Task> tasks) {
        if (tasks == null) {
            return null;
        }
        List<TaskTo> list = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            list.add(toTo(task));
        }
        return list;
    }

    protected Activity activityToToActivity(ActivityTo activityTo) {
        if (activityTo == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(activityTo.getId());
        activity.setAuthor(activityTo.getAuthor());
        activity.setTask(toEntity(activityTo.getTask()));
        activity.setComment(activityTo.getComment());
        activity.setUpdated(activityTo.getUpdated());
        activity.setStatusCode(activityTo.getStatusCode());
        activity.setPriorityCode(activityTo.getPriorityCode());
        activity.setTypeCode(activityTo.getTypeCode());
        activity.setEstimate(activityTo.getEstimate());
        return activity;
    }

    protected List<Activity> activityToListToActivityList(List<ActivityTo> list) {
        if (list == null) {
            return null;
        }
        List<Activity> list1 = new ArrayList<>(list.size());
        for (ActivityTo activityTo : list) {
            list1.add(activityToToActivity(activityTo));
        }
        return list1;
    }

    protected ActivityTo activityToActivityTo(Activity activity, TaskTo taskTo) {
        if (activity == null) {
            return null;
        }
        Long id = activity.getId();
        User author = activity.getAuthor();
        TaskTo task = taskTo;
        LocalDateTime updated = activity.getUpdated();
        String comment = activity.getComment();
        String statusCode = activity.getStatusCode();
        String priorityCode = activity.getPriorityCode();
        String typeCode = activity.getTypeCode();
        Integer estimate = activity.getEstimate();
        ActivityTo activityTo = new ActivityTo(id, author, task, updated, comment, statusCode, priorityCode,
                typeCode, estimate);
        return activityTo;
    }

    protected List<ActivityTo> activityListToActivityToList(List<Activity> list, TaskTo taskTo) {
        if (list == null) {
            return null;
        }
        List<ActivityTo> list1 = new ArrayList<>(list.size());
        for (Activity activity : list) {
            list1.add(activityToActivityTo(activity, taskTo));
        }
        return list1;
    }

    protected TaskTo taskToTaskTo(Task task) {
        if (task == null) {
            return null;
        }
        List<ActivityTo> activities = null;
        int estimate = 0;
        Long id = task.getId();
        String title = task.getTitle();
        Set<String> tags = task.getTags();
        if (tags != null) {
            tags = new LinkedHashSet<>(tags);
        }
        boolean enabled = task.isEnabled();
        String typeCode = task.getTypeCode();
        String statusCode = task.getStatusCode();
        String description = task.getDescription();
        SprintTo sprint = sprintMapper.toTo(task.getSprint());
        ProjectTo project = projectMapper.toTo(task.getProject());
        LocalDateTime updated = task.getUpdated();
        String priorityCode = task.getPriorityCode();
        if (task.getEstimate() != null) {
            estimate = task.getEstimate();
        }
        TaskTo parent = toTo(task.getParent());
        int storyPoints = 0;
        TaskTo taskTo = new TaskTo(id, title, enabled, typeCode, statusCode, description, sprint, project,
                updated, priorityCode, estimate, storyPoints, tags, activities, parent);
        activities = activityListToActivityToList(task.getActivities(), taskTo);
        TaskTo updatedTaskTo = new TaskTo(id, title, enabled, typeCode, statusCode, description, sprint,
                project, updated, priorityCode, estimate, storyPoints, tags, activities, parent);
        return updatedTaskTo;
    }

    protected Task taskToToTask(TaskTo taskTo) {
        if (taskTo == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskTo.getId());
        task.setEnabled(taskTo.isEnabled());
        task.setTitle(taskTo.getTitle());
        task.setTypeCode(taskTo.getTypeCode());
        task.setStatusCode(taskTo.getStatusCode());
        task.setPriorityCode(taskTo.getPriorityCode());
        task.setDescription(taskTo.getDescription());
        task.setProject(projectMapper.toEntity(taskTo.getProject()));
        task.setSprint(sprintMapper.toEntity(taskTo.getSprint()));
        task.setUpdated(taskTo.getUpdated());
        task.setEstimate(taskTo.getEstimate());
        Set<String> set = taskTo.getTags();
        if (set != null) {
            task.setTags(new LinkedHashSet<>(set));
        }
        task.setActivities(activityToListToActivityList(taskTo.getActivities()));
        task.setParent(toEntity(taskTo.getParent()));
        return task;
    }
}
