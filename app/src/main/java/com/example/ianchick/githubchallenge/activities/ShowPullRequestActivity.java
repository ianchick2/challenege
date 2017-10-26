package com.example.ianchick.githubchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ianchick.githubchallenge.PullRequest;
import com.example.ianchick.githubchallenge.R;
import com.example.ianchick.githubchallenge.utilities.HttpGetRequest;

import java.util.concurrent.ExecutionException;

public class ShowPullRequestActivity extends AppCompatActivity {

    TextView diffTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pull_request);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PullRequest pullRequest = (PullRequest) getIntent().getSerializableExtra("PULL_REQUEST_OBJ");

        setTitle(pullRequest.getTitle());
        diffTextView = findViewById(R.id.diff);

        try {
            diffTextView.setText(parseDiff(pullRequest.getDiffUrl()));
        } catch (InterruptedException | ExecutionException e) {
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
        return HttpGetRequest.getRequest(diffUrl);
    }
}
