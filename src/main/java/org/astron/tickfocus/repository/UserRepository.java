package org.astron.tickfocus.repository;

import org.astron.tickfocus.entity.DatabaseTimerSettings;
import org.astron.tickfocus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.timerSettings = ?2 where u.id = ?1")
    void updateTimerSettings(Long userId, DatabaseTimerSettings databaseTimerSettings);
}
