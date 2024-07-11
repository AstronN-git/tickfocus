package org.astron.tickfocus.service;

import org.astron.tickfocus.entity.User;
import org.astron.tickfocus.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.SimpleErrors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    void testFindsUserByUsername() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        when(userRepository.findUserByUsername("user"))
                .thenReturn(Optional.of(user));

        assertEquals(userService.loadUserByUsername("user"), user);
    }

    @Test
    void testThrowsUsernameNotFoundExceptionIfUserDoesNotExists() {
        when(userRepository.findUserByUsername("user"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("user"));
    }

    @Test
    void testUserIsSavedOnRegisterUser() {
        User user = new User();

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        User returnedUser = userService.registerUser(user);
        assertNotNull(returnedUser);
    }

    @Test
    void testFindsUserByEmail() {
        User user = new User();

        when(userRepository.findUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));

        UserDetails returnedUser = userService.loadUserByUsername("example@gmail.com");
        assertNotNull(returnedUser);
    }

    @Test
    void testUniqueUserValidationWhenUserIsUnique() {
        when(userRepository.existsByUsername("notexists")).thenReturn(false);
        when(userRepository.existsByEmail("notexists@gmail.com")).thenReturn(false);

        User user = new User();
        user.setUsername("notexists");
        user.setEmail("notexists@gmail.com");

        Errors errors = new SimpleErrors(user);

        userService.validateUniqueFields(user, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void testUniqueUserValidationWhenUsernameOrEmailIsTaken() {
        when(userRepository.existsByUsername("notexists")).thenReturn(false);
        when(userRepository.existsByEmail("notexists@gmail.com")).thenReturn(false);
        when(userRepository.existsByUsername("exists")).thenReturn(true);
        when(userRepository.existsByEmail("exists@gmail.com")).thenReturn(true);

        User user = new User();
        user.setUsername("exists");
        user.setEmail("notexists@gmail.com");
        Errors errors = new SimpleErrors(user);

        userService.validateUniqueFields(user, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("username"));

        user.setUsername("notexists");
        user.setEmail("exists@gmail.com");
        errors = new SimpleErrors(user);

        userService.validateUniqueFields(user, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("email"));
    }
}
