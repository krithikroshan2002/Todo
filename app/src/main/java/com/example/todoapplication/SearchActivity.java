package com.example.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageButton addTodo;
    private ImageButton menuButton;
    private Spinner statusBar;
    private List<String> todoStatuses;
    private ArrayAdapter<String> statusAdapter;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        final Intent intent = getIntent();
        final String parentId = intent.getStringExtra("projectId");
        final String parentName = intent.getStringExtra("projectName");
        addTodo = findViewById(R.id.add_todo_search);
        menuButton = findViewById(R.id.menu_search);
        statusBar = findViewById(R.id.search_bar_status);
        todoStatuses = new ArrayList<>();
        todoStatuses.add("status");
        todoStatuses.add("checked");
        todoStatuses.add("unchecked");
        statusAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, todoStatuses);
        statusBar.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent todoIntent = new Intent(SearchActivity.this, ProjectActivity.class);
                todoIntent.putExtra("projectId", parentId);
                todoIntent.putExtra("projectName", parentName);
                startActivity(todoIntent);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
