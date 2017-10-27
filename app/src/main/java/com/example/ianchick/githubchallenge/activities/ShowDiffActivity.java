package com.example.ianchick.githubchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.ianchick.githubchallenge.Diff;
import com.example.ianchick.githubchallenge.PullRequest;
import com.example.ianchick.githubchallenge.R;
import com.example.ianchick.githubchallenge.adapters.DiffListAdapter;
import com.example.ianchick.githubchallenge.utilities.DiffParser;
import com.example.ianchick.githubchallenge.utilities.HttpGetRequest;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ShowDiffActivity extends AppCompatActivity {

    private ListView listDiffs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diff);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listDiffs = findViewById(R.id.list_diff);

        PullRequest pullRequest = (PullRequest) getIntent().getSerializableExtra("PULL_REQUEST_OBJ");

        setTitle(pullRequest.getTitle());

        try {
            String rawDiff = parseDiff(pullRequest.getDiffUrl());
            String[] splitDiffs = DiffParser.splitByClass(rawDiff);
            ArrayList<Diff> diffs = new ArrayList<>();
            for (String d : splitDiffs) {
                diffs.add(new Diff(d));
            }
            DiffListAdapter diffListAdapter = new DiffListAdapter(this, diffs);
            listDiffs.setAdapter(diffListAdapter);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Private
     */

    private String parseDiff(String diffUrl) throws ExecutionException, InterruptedException {
        return HttpGetRequest.getRequest(diffUrl, this);
    }
}
