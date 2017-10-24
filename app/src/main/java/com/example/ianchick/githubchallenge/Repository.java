package com.example.ianchick.githubchallenge;

/**
 * Created by ianchick on 10/23/17.
 */

public class Repository {

    private String fullName;
    private String url;

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
