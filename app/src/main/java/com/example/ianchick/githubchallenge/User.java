package com.example.ianchick.githubchallenge;

/**
 * Created by ianchick on 10/23/17.
 */

public class User {

    private final String login;
    private final String name;
    private final String reposUrl;

    public User(String login, String name, String reposUrl) {
        this.login = login;
        this.name = name;
        this.reposUrl = reposUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getReposUrl() {
        return reposUrl;
    }
}
