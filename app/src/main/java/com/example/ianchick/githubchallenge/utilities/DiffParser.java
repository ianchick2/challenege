package com.example.ianchick.githubchallenge.utilities;

/**
 * Created by ianchick on 10/26/17.
 */

public class DiffParser {

    public DiffParser(String diff) {

    }

    /**
     * Private
     */

    // Gets rid of 'diff --git' string currently
    private String[] splitByClass(String diff) {
        String[] splitDiffByClass = diff.split("diff --git");
        return splitDiffByClass;
    }


}
