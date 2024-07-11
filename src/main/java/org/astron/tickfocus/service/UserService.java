package org.astron.tickfocus.service;

import org.astron.tickfocus.configuration.DefaultTimerSettings;
import org.astron.tickfocus.entity.DatabaseTimerSettings;
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
    private final DefaultTimerSettings defaultTimerSettings;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, DefaultTimerSettings defaultTimerSettings) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultTimerSettings = defaultTimerSettings;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        DatabaseTimerSettings timerSettings = new DatabaseTimerSettings();
        timerSettings.setRestingTime(defaultTimerSettings.getRestingTime());
        timerSettings.setWorkingTime(defaultTimerSettings.getWorkingTime());

        user.setTimerSettings(timerSettings);
        log.info("User created: {}", user);
        return userRepository.save(user);
    }

    public void validateUniqueFields(User user, Errors errors) {
        if (userRepository.existsByUsername(user.getUsername())) {
            errors.rejectValue("username", "taken", "Username is already taken");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            errors.rejectValue("email", "taken", "Email is already taken");
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
