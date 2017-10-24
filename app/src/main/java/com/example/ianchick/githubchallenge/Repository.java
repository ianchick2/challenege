package com.example.ianchick.githubchallenge;

/**
 * Created by ianchick on 10/23/17.
 */

public class Repository {

    private String fullName;
    private String pullRequestUrl;

    public Repository(String fullName, String pullRequestUrl) {
        this.fullName = fullName;
        this.pullRequestUrl = pullRequestUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPullRequestUrl() {
        return pullRequestUrl;
    }
}
