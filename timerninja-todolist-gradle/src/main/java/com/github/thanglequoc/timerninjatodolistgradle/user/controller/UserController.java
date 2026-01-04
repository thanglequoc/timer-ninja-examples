package com.github.thanglequoc.timerninjatodolistgradle.user.controller;

import com.github.thanglequoc.timerninjatodolistgradle.user.User;
import com.github.thanglequoc.timerninjatodolistgradle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createNewUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }

    @GetMapping("/{username}")
    public User getUserName(@PathVariable String username) {
        return userService.getUserByName(username);
    }
}
