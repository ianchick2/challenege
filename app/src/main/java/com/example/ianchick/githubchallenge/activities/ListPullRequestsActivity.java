package com.example.ianchick.githubchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.ianchick.githubchallenge.PullRequest;
import com.example.ianchick.githubchallenge.R;
import com.example.ianchick.githubchallenge.adapters.PullRequestListAdapter;
import com.example.ianchick.githubchallenge.utilities.HttpGetRequest;
import com.example.ianchick.githubchallenge.utilities.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListPullRequestsActivity extends AppCompatActivity {

    private ListView listPullRequests;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pull_requests);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.pull_requests));

        String pullRequestUrl = getIntent().getStringExtra("LIST_PULL_REQUESTS_URL");

        listPullRequests = findViewById(R.id.list_pulls);
        emptyView = findViewById(R.id.empty_fragment);

        emptyView.setVisibility(View.GONE);

        try {
            ArrayList<PullRequest> pullRequests = parsePullRequest(pullRequestUrl);
            PullRequestListAdapter pullRequestListAdapter = new PullRequestListAdapter(pullRequests, this);
            listPullRequests.setAdapter(pullRequestListAdapter);
        } catch (ExecutionException | InterruptedException | JSONException e) {
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

    private ArrayList<PullRequest> parsePullRequest(String pullRequestUrl) throws ExecutionException, InterruptedException, JSONException {
        ArrayList<PullRequest> pullRequests = new ArrayList<>();
        JSONArray jsonArray = JsonParser.getJsonArray(HttpGetRequest.getRequest(pullRequestUrl));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            pullRequests.add(new PullRequest(jsonObject.getInt("number"), jsonObject.getString("url"),
                                            jsonObject.getString("state"), jsonObject.getString("diff_url"), jsonObject.getString("title")));
        }
        if (pullRequests.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }
        return pullRequests;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
