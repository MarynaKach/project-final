package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.Activity;
import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Transactional(readOnly = true)
public interface ActivityRepository extends BaseRepository<Activity> {
    @Transactional
    @Query(value = "SELECT activity.updated FROM  activity WHERE task_id = :taskId AND status_code = :statusCode", nativeQuery = true)
    Timestamp getUpdateTimeByStatus(@Param("taskId") Long taskId, @Param("statusCode") String statusCode);
}
