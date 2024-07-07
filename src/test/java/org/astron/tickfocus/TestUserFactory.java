package org.astron.tickfocus;

import org.astron.tickfocus.entity.User;

public class TestUserFactory {
    public static User createTestUser() {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("123");
        testUser.setEmail("test@gmail.com");
        testUser.setId(1L);

        return testUser;
    }
}
