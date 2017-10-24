package com.example.ianchick.githubchallenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ianchick.githubchallenge.R;
import com.example.ianchick.githubchallenge.Repository;

import java.util.ArrayList;

import static com.example.ianchick.githubchallenge.R.id.url;

/**
 * Created by ianchick on 10/24/17.
 */

public class RepositoryListAdapter extends ArrayAdapter implements View.OnClickListener {

    public RepositoryListAdapter(ArrayList<Repository> data, Context context) {
        super(context, R.layout.repository_list_row, data);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View view = contentView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.repository_list_row, null);;
        }

        Repository repository = (Repository) getItem(position);

        if (repository != null) {
            TextView fullNameTextView = view.findViewById(R.id.full_name);
            TextView urlTextView = view.findViewById(url);
            fullNameTextView.setText(repository.getFullName());
            urlTextView.setText(repository.getUrl());
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        TextView urlTextView = view.findViewById(R.id.url);
        String url = urlTextView.getText().toString();
        String urlPullRequests = url + "/pulls";
    }
}
