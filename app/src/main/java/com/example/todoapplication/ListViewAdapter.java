package com.example.todoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapplication.model.Project;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Project> {

    final Activator activator = new Activator();
    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Project> objects) {
        super(context, R.layout.activity_main, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Project project = getItem(position);

        if (convertView ==  null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }
        final TextView projectName = (TextView)convertView.findViewById(R.id.listProjectName);
        final Button remove = (Button) convertView.findViewById(R.id.listRemove);

        projectName.setText(project.toString());

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activator.removeProject(position);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activator.onClickProject(position);
            }
        });

        return convertView;
    }
}
