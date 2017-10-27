package com.example.ianchick.githubchallenge;

/**
 * Created by ianchick on 10/26/17.
 */

public class Diff {

    private String className;
    private String body;

    public Diff(String body) {
        this.body = body;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getClassName() {
        return className;
    }

    public String getBody() {
        return body;
    }
}
