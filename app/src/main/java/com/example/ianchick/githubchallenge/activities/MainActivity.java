package com.example.ianchick.githubchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

    final private String BASE_URL = "https://username:ianchick2@api.github.com/";

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

        usernameInput = findViewById(R.id.username_input);
        usernameTextView = findViewById(R.id.show_username);
        nameTextView = findViewById(R.id.show_name);
        reposUrlTextView = findViewById(R.id.show_repos_url);

        listRepos = findViewById(R.id.list_repos);

    }

    public void submitUsername(View view) throws ExecutionException, InterruptedException, JSONException {
        clearViews();
        activeUser = parseUser(usernameInput.getText().toString());
        if (activeUser != null) {
            usernameTextView.setText(activeUser.getLogin());
            nameTextView.setText(activeUser.getName());
            reposUrlTextView.setText(activeUser.getReposUrl());
            Utils.hideKeyboard(this);
        } else {
            usernameTextView.setText("No user with that name was found.");
        }
    }

    public void listRepos(View view) throws ExecutionException, InterruptedException, JSONException {
        ArrayList<Repository> repositories = parseRepos(activeUser.getReposUrl());
        if (!repositories.isEmpty()) {
            RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter(repositories, this);
            listRepos.setAdapter(repositoryListAdapter);
            Utils.hideKeyboard(this);
        } else {
            usernameTextView.setText("No repositories found.");
        }
    }

    /**
     * Private
     */

    private User parseUser(String username) throws ExecutionException, InterruptedException, JSONException {
        String url = BASE_URL + "users/" + username;
        String result = HttpGetRequest.getRequest(url);
        if (!TextUtils.isDigitsOnly(result)) {
            JSONObject jsonObject = JsonParser.getJsonObject(result);
            return new User(jsonObject.getString("login"), jsonObject.getString("name"), jsonObject.getString("repos_url"));
        } else {
            return null;
        }
    }

    private ArrayList<Repository> parseRepos(String url) throws ExecutionException, InterruptedException, JSONException {
        ArrayList<Repository> repositories = new ArrayList<>();
        String result = HttpGetRequest.getRequest(url);
        if (!TextUtils.isDigitsOnly(result)) {
            JSONArray jsonArray = JsonParser.getJsonArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                repositories.add(new Repository(jsonObject.getString("full_name"), jsonObject.getString("url")));
            }
        }
        return repositories;
    }

    private void clearViews() {
        listRepos.setAdapter(null);
        usernameTextView.setText("");
        nameTextView.setText("");
        reposUrlTextView.setText("");
    }
}
