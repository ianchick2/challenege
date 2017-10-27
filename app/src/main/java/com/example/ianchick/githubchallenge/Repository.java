package com.example.ianchick.githubchallenge;

/**
 * Created by ianchick on 10/23/17.
 */

public class Repository {

    private final String fullName;
    private final String url;

    public Repository(String fullName, String url) {
        this.fullName = fullName;
        this.url = url;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUrl() {
        return url;
    }
}
