package com.github.thanglequoc.timerninjatodolistgradle.user;

import io.github.thanglequoc.timerninja.TimerNinjaTracker;

public class User {
    private String username;

    public User() {}

    @TimerNinjaTracker(includeArgs = true)
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
