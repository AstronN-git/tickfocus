package org.astron.tickfocus;

import org.astron.tickfocus.entity.User;
import org.astron.tickfocus.repository.UserRepository;
import org.astron.tickfocus.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void testFindsUserByUsername() {
        User user = new User();
        user.setId(1L);
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
    void testUserIsSavedOnSave() {
        User user = new User();

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        User returnedUser = userService.save(user);
        assertNotNull(returnedUser);
        assertEquals(user, returnedUser);
    }
}
