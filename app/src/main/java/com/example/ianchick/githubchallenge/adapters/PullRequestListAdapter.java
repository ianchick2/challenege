package com.example.ianchick.githubchallenge.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ianchick.githubchallenge.PullRequest;
import com.example.ianchick.githubchallenge.R;

import java.util.ArrayList;

/**
 * Created by ianchick on 10/24/17.
 */

public class PullRequestListAdapter extends ArrayAdapter {

    public PullRequestListAdapter(ArrayList<PullRequest> data, Context context) {
        super(context, R.layout.pullrequest_list_row, data);
    }

    @NonNull
    @Override
    public View getView(int position, View contentView, @NonNull ViewGroup parent) {
        View view = contentView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.pullrequest_list_row, null);;
        }

        PullRequest pullRequest = (PullRequest) getItem(position);

        if (pullRequest != null) {
            TextView idTextView = view.findViewById(R.id.id);
            TextView urlTextView = view.findViewById(R.id.url);
            TextView diffUrlTextView = view.findViewById(R.id.diff_url);
            TextView stateTextView = view.findViewById(R.id.state);

            idTextView.setText(String.valueOf(pullRequest.getId()));
            urlTextView.setText(pullRequest.getUrl());
            diffUrlTextView.setText(pullRequest.getDiffUrl());
            stateTextView.setText(pullRequest.getState());
        }

        return view;
    }
}
