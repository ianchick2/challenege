package com.example.ianchick.githubchallenge;

/**
 * Created by ianchick on 10/24/17.
 */

public class PullRequest {

    private int id;
    private String url;
    private String state;
    private String diffUrl;

    public PullRequest(int id, String url, String state, String diffUrl) {
        this.id = id;
        this.url = url;
        this.state = state;
        this.diffUrl = diffUrl;
    }

    public int getId() {
        return id;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getState() {
        return state;
    }
}
