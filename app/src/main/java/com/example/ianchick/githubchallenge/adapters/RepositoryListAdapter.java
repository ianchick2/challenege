package com.example.ianchick.githubchallenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ianchick.githubchallenge.R;
import com.example.ianchick.githubchallenge.Repository;

import java.util.ArrayList;

/**
 * Created by ianchick on 10/24/17.
 */

public class RepositoryListAdapter extends ArrayAdapter implements View.OnClickListener {

    private String fullName;
    private String url;

    private ArrayList<String> data;
    private Context context;

    private static class ViewHolder {
        TextView fullNameTextView;
        TextView urlTextView;
    }

    public RepositoryListAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.repository_list_row, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View view = contentView;
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.repository_list_row, null);;
            holder = new ViewHolder();
            holder.fullNameTextView = view.findViewById(R.id.full_name);
            holder.urlTextView = view.findViewById(R.id.url);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Repository repository = (Repository) getItem(position);

        if (repository != null) {
            holder.fullNameTextView.setText(fullName);
            holder.urlTextView.setText(url);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "Hello, nothing happens yet!", Toast.LENGTH_SHORT).show();
    }
}
