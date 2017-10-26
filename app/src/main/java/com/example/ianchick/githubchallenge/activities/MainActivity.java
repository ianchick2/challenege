package com.example.ianchick.githubchallenge.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

    private static final String BASE_URL = "https://api.github.com/";

    private TextView usernameTextView;
    private TextView nameTextView;
    private TextView reposUrlTextView;
    private ListView listRepos;
    private ImageView imageLogo;

    private User activeUser;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.android_interview_challenge));

        usernameTextView = findViewById(R.id.show_username);
        nameTextView = findViewById(R.id.show_name);
        reposUrlTextView = findViewById(R.id.show_repos_url);
        imageLogo = findViewById(R.id.image_logo);
        imageLogo.setVisibility(View.VISIBLE);

        listRepos = findViewById(R.id.list_repos);
        searchView = findViewById(R.id.search_view);
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.parseColor("#ffffff"));
        searchEditText.setHintTextColor(Color.parseColor("#A9CCE3"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    submitUsername(query);
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                clearViews();
                if (newText.isEmpty()) {
                    imageLogo.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    public void submitUsername(String username) throws ExecutionException, InterruptedException, JSONException {
        clearViews();
        activeUser = parseUser(username);
        if (activeUser != null) {
            usernameTextView.setText(String.format("%s%s%s", getString(R.string.user_label), " ", activeUser.getLogin()));
            nameTextView.setText(String.format("%s%s%s", getString(R.string.name_label), " ", activeUser.getName()));
            reposUrlTextView.setText(String.format("%s%s%s", getString(R.string.repos_label), " ", activeUser.getReposUrl()));
            Utils.hideKeyboard(this);
        }
    }

    public void listRepos(View view) throws ExecutionException, InterruptedException, JSONException {
        imageLogo.setVisibility(View.GONE);
        setTitle(activeUser.getLogin() + getString(R.string._repositories));
        ArrayList<Repository> repositories = parseRepos(activeUser.getReposUrl());
        if (!repositories.isEmpty()) {
            RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter(repositories, this);
            listRepos.setAdapter(repositoryListAdapter);
            Utils.hideKeyboard(this);
        } else {
            usernameTextView.setText(R.string.no_repos_found_msg);
        }
    }

    /**
     * Private
     */

    private User parseUser(String username) throws ExecutionException, InterruptedException, JSONException {
        String url = BASE_URL + "users/" + username;
        String result = HttpGetRequest.getRequest(url);
        if (result != null && !TextUtils.isDigitsOnly(result)) {
            JSONObject jsonObject = JsonParser.getJsonObject(result);
            return new User(jsonObject.getString("login"), jsonObject.getString("name"), jsonObject.getString("repos_url"));
        } else if(Integer.parseInt(result) == 403) {
            usernameTextView.setText(R.string.four_oh_three_response);
        } else {
            usernameTextView.setText(R.string.no_user_found_msg);
        }
        return null;
    }

    private ArrayList<Repository> parseRepos(String url) throws ExecutionException, InterruptedException, JSONException {
        ArrayList<Repository> repositories = new ArrayList<>();
        String result = HttpGetRequest.getRequest(url);
        if (result != null && !TextUtils.isDigitsOnly(result)) {
            JSONArray jsonArray = JsonParser.getJsonArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                repositories.add(new Repository(jsonObject.getString("full_name"), jsonObject.getString("url")));
            }
        }
        return repositories;
    }

    private void clearViews() {
        setTitle(getString(R.string.android_interview_challenge));
        listRepos.setAdapter(null);
        usernameTextView.setText("");
        nameTextView.setText("");
        reposUrlTextView.setText("");
    }
}
