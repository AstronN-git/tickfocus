package org.astron.tickfocus.service;

import org.astron.tickfocus.entity.TimerState;
import org.astron.tickfocus.entity.User;
import org.astron.tickfocus.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        TimerState timerState = new TimerState();
        user.setTimerState(timerState);
        log.info("User created: {}", user);
        return userRepository.save(user);
    }

    public void validateUniqueFields(User user, Errors errors) {
        if (userRepository.existsByEmail(user.getEmail())) {
            errors.rejectValue("email", "is already taken");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            errors.rejectValue("username", "is already taken");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserByUsername(username)
                .or(() -> userRepository.findUserByEmail(username))
                .orElseThrow(() -> {
                    log.info("Username {} not found", username);
                    return new UsernameNotFoundException("Username " + username + " not found");
                });
    }
}
