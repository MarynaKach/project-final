package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TaskRepository extends BaseRepository<Task> {
    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.sprint LEFT JOIN FETCH t.activities")
    List<Task> getAll();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO  task_tag (task_id, tag) VALUES (:taskId, :tag)", nativeQuery = true)
    void saveTaskTag(@Param("taskId") Long taskId, @Param("tag") String tag);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM task_tag WHERE task_id = :taskId", nativeQuery = true)
    void deleteTagByTaskId(@Param("taskId") Long taskId);
}
