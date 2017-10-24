package com.example.ianchick.githubchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ianchick.githubchallenge.R;
import com.example.ianchick.githubchallenge.Repository;
import com.example.ianchick.githubchallenge.User;
import com.example.ianchick.githubchallenge.adapters.RepositoryListAdapter;
import com.example.ianchick.githubchallenge.utilities.HttpGetRequest;
import com.example.ianchick.githubchallenge.utilities.JsonParser;
import com.example.ianchick.githubchallenge.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    final private String BASE_URL = "https://api.github.com/";

    private TextView usernameTextView;
    private TextView nameTextView;
    private TextView reposUrlTextView;
    private EditText usernameInput;
    private ListView listRepos;

    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = (EditText) findViewById(R.id.username_input);
        usernameTextView = (TextView) findViewById(R.id.show_username);
        nameTextView = (TextView) findViewById(R.id.show_name);
        reposUrlTextView = (TextView) findViewById(R.id.show_repos_url);

        listRepos = (ListView) findViewById(R.id.list_repos);

    }

    public void submitUsername(View view) throws ExecutionException, InterruptedException, JSONException {
        activeUser = parseUser(usernameInput.getText().toString());
        usernameTextView.setText(activeUser.getLogin());
        nameTextView.setText(activeUser.getName());
        reposUrlTextView.setText(activeUser.getReposUrl());
        Utils.hideKeyboard(this);
    }

    public void listRepos(View view) throws ExecutionException, InterruptedException, JSONException {
        ArrayList<Repository> repositories = parseRepos(activeUser.getReposUrl());
        RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter(repositories, this);
        listRepos.setAdapter(repositoryListAdapter);
        Utils.hideKeyboard(this);
    }

    /**
     * Private
     */

    private User parseUser(String username) throws ExecutionException, InterruptedException, JSONException {
        String url = BASE_URL + "users/" + username;
        JSONObject jsonObject = JsonParser.getJsonObject(HttpGetRequest.getRequest(url));
        return new User(jsonObject.getString("login"), jsonObject.getString("name"), jsonObject.getString("repos_url"));
    }

    private ArrayList<Repository> parseRepos(String url) throws ExecutionException, InterruptedException, JSONException {
        ArrayList<Repository> repositories = new ArrayList<>();
        JSONArray jsonArray = JsonParser.getJsonArray(HttpGetRequest.getRequest(url));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            repositories.add(new Repository(jsonObject.getString("full_name"), jsonObject.getString("url")));
        }
        return repositories;
    }
}
