package com.github.thanglequoc.timerninjatodolistmaven.service;

import com.github.thanglequoc.timerninjatodolistmaven.user.User;
import com.github.thanglequoc.timerninjatodolistmaven.user.service.UserService;
import io.github.thanglequoc.timerninja.TimerNinjaConfiguration;
import io.github.thanglequoc.timerninja.TimerNinjaTracker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeAll
    public static void beforeAll() {
        TimerNinjaConfiguration.getInstance().toggleSystemOutLog(false);
    }

    @TimerNinjaTracker
    @Test
    public void testGetUserName() {
        User user = userService.getUserByName("ninja");
        assertEquals("ninja", user.getUsername());
        System.out.println("Test completed");
    }

}
