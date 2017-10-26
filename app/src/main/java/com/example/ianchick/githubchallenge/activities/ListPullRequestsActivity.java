package com.example.ianchick.githubchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pull_requests);

        setTitle("Pull Requests");

        String pullRequestUrl = getIntent().getStringExtra("LIST_PULL_REQUESTS_URL");

        listPullRequests = (ListView) findViewById(R.id.list_pulls);

        try {
            ArrayList<PullRequest> pullRequests = parsePullRequest(pullRequestUrl);
            PullRequestListAdapter pullRequestListAdapter = new PullRequestListAdapter(pullRequests, this);
            listPullRequests.setAdapter(pullRequestListAdapter);
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
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
        return pullRequests;
    }
}
