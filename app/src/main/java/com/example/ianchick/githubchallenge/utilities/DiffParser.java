package com.example.ianchick.githubchallenge.utilities;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ianchick on 10/26/17.
 */

public class DiffParser {

    // Gets rid of 'diff --git' string currently
    public static String[] splitByClass(String diff) {
        String[] splitDiffByClass = diff.split("diff --git ");
        return splitDiffByClass;
    }

    public static void splitByLine(String diff, LinearLayout layout, Context context) {
        String[] lines = diff.split("\n");
        for (String s : lines) {
            TextView newTextView = new TextView(context);
            newTextView.setText(s);
            layout.addView(newTextView);
        }
    }

    /**
     * Private
     */




}
