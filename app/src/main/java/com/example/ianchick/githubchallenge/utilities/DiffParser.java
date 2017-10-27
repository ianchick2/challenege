package com.example.ianchick.githubchallenge.utilities;

/**
 * Created by ianchick on 10/26/17.
 */

public class DiffParser {

    // Gets rid of 'diff --git' string currently
    public static String[] splitByClass(String diff) {
        String[] splitDiffByClass = diff.split("diff --git ");
        return splitDiffByClass;
    }

    /**
     * Private
     */




}
