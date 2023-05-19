package com.javarush.jira.bugtracking.internal.mapper;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.common.BaseMapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

public interface TaskMapper extends BaseMapper<Task, TaskTo> {

    @Mapping(target = "enabled", expression = "java(task.isEnabled())")
    @Override
    TaskTo toTo(Task task);

    @Override
    Task toEntity(TaskTo taskTo);

    @Override
    List<TaskTo> toToList(Collection<Task> tasks);
}
