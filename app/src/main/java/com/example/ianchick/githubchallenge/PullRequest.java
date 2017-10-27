package com.example.ianchick.githubchallenge;

import java.io.Serializable;

/**
 * Created by ianchick on 10/24/17.
 */

public class PullRequest implements Serializable {

    private final int number;
    private final String url;
    private final String state;
    private final String diffUrl;
    private final String title;

    public PullRequest(int number, String url, String state, String diffUrl, String title) {
        this.number = number;
        this.url = url;
        this.state = state;
        this.diffUrl = diffUrl;
        this.title = title;
    }

    public int getNumber() {
        return number;
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

    public String getTitle() {
        return title;
    }
}
