package com.example.ianchick.githubchallenge.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ianchick.githubchallenge.Diff;
import com.example.ianchick.githubchallenge.R;

import java.util.ArrayList;

/**
 * Created by ianchick on 10/26/17.
 */

public class DiffListAdapter extends ArrayAdapter {

    private Context context;
    private TextView diffBody;

    public DiffListAdapter(Context context, ArrayList<Diff> data) {
        super(context, R.layout.diff_list_row, data);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View contentView, @NonNull ViewGroup parent) {
        View view = contentView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.diff_list_row, null);;
        }

        final Diff diff = (Diff) getItem(position);

        if (diff != null) {
            diffBody = view.findViewById(R.id.diff_body);
            diffBody.setText(diff.getBody());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }
}
