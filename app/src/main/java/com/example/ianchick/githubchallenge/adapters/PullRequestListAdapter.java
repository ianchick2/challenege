package com.example.ianchick.githubchallenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ianchick.githubchallenge.PullRequest;
import com.example.ianchick.githubchallenge.R;
import com.example.ianchick.githubchallenge.activities.ShowPullRequestActivity;

import java.util.ArrayList;

/**
 * Created by ianchick on 10/24/17.
 */

public class PullRequestListAdapter extends ArrayAdapter {

    private Context context;

    public PullRequestListAdapter(ArrayList<PullRequest> data, Context context) {
        super(context, R.layout.pullrequest_list_row, data);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View contentView, @NonNull ViewGroup parent) {
        View view = contentView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.pullrequest_list_row, null);;
        }

        final PullRequest pullRequest = (PullRequest) getItem(position);

        if (pullRequest != null) {
            TextView numberTextView = view.findViewById(R.id.number);
            TextView urlTextView = view.findViewById(R.id.url);
            TextView diffUrlTextView = view.findViewById(R.id.diff_url);
            TextView stateTextView = view.findViewById(R.id.state);
            TextView titleTextView = view.findViewById(R.id.title);

            numberTextView.setText(String.valueOf(pullRequest.getNumber()));
            urlTextView.setText(pullRequest.getUrl());
            diffUrlTextView.setText(pullRequest.getDiffUrl());
            stateTextView.setText(pullRequest.getState());
            titleTextView.setText(pullRequest.getTitle());

        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowPullRequestActivity.class);
                intent.putExtra("PULL_REQUEST_OBJ", pullRequest);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
