package org.astron.tickfocus.repository;

import org.astron.tickfocus.entity.TimerState;
import org.astron.tickfocus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface TimerStateRepository extends JpaRepository<TimerState, Long> {

    @Transactional
    @Modifying
    @Query("update TimerState t set t.timerStarted = ?1 where t.user = ?2")
    void updateTimerStartedByUser(LocalDateTime timerStarted, User user);

    TimerState findByUser(User user);
}
