package com.javarush.jira.login.internal;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.common.BaseRepository;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.javarush.jira.login.internal.config.SecurityConfig.PASSWORD_ENCODER;

//import static com.javarush.jira.login.internal.config.SecurityConfig.PASSWORD_ENCODER;

@Transactional(readOnly = true)
@CacheConfig(cacheNames = "users")
public interface UserRepository extends BaseRepository<User> {

    @Cacheable(key = "#email")
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    @Transactional
    @CachePut(key = "#user.email")
    default User prepareAndCreate(User user) {
        return prepareAndUpdate(user, PASSWORD_ENCODER.encode(user.getPassword()));
    }
    @Transactional
    @CacheEvict(key = "#user.email")
    default User prepareAndUpdate(User user, String encPassword) {
        user.setPassword(encPassword);
        user.normalize();
        return save(user);
    }

    default User getExistedByEmail(String email) {
        return findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO  subscribed_task (task_id, user_id) VALUES (:taskId, :userId)", nativeQuery = true)
    void subscribeToTask(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Transactional
    @Query(value = "SELECT t.* FROM task t JOIN subscribed_task st ON t.id = st.task_id WHERE st.user_id = :userId", nativeQuery = true)
    List<String> getAllSubscriptions(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM subscribed_task WHERE task_id = :taskId AND user_id = :userId", nativeQuery = true)
    void unsubscribeFromTask(@Param("taskId") Long taskId, @Param("userId") Long userId);


    @Query(value = "SELECT COUNT(*) FROM subscribed_task WHERE user_id = :userId AND task_id = :taskId", nativeQuery = true)
    Long checkIfSubscribed(@Param("userId") Long userId, @Param("taskId") Long taskId);

}
