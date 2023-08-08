package com.example.todoapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.todoapplication.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class ProjectActivity extends AppCompatActivity {

    private ImageButton menuButton;
    private EditText editText;
    private static List<Todo> todos = new ArrayList<>();;
    private TextView name;
    private static Long todoId = 1L;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project);
        menuButton = findViewById(R.id.menuButton1);
        final String projectName = getIntent().getStringExtra("name");
        viewTodo(projectName);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });

        editText = findViewById(R.id.todoEditText);
        name = findViewById(R.id.projectName);
        name.setText(projectName);

        final Button addButton = findViewById(R.id.button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                addTodo(projectName);
            }
        });

    }

    public void addTodo(final String parentId) {
        final String todoItem = editText.getText().toString();
        final Todo todo = new Todo(String.valueOf(todoId++), todoItem, false, parentId);
        todos.add(todo);
        if(todos != null) {
            final TableLayout tableLayout = findViewById(R.id.tableLayout);
            final TableRow tableRow = new TableRow(this);
            final CheckBox checkBox = new CheckBox(this);
            final TextView todoView = new TextView(this);
            final ImageView closeIcon = new ImageView(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.addView(checkBox);
            todoView.setText(todoItem);
            tableRow.addView(todoView);
            closeIcon.setImageResource(R.drawable.baseline_close_24);
            closeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    tableLayout.removeView(tableRow);
                    ;
                }
            });

            tableRow.addView(closeIcon);
            tableLayout.addView(tableRow);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    if (isChecked) {
                        todoView.setTextColor(ContextCompat.getColor(ProjectActivity.this, android.R.color.darker_gray));
                        todo.setChecked();
                    } else {
                        todoView.setTextColor(Color.BLACK);
                    }
                }
            });
            editText.getText().clear();
        }
    }

    public void viewTodo(final String parentId) {
        final TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (final Todo todo : todos) {
            if (todo.getParentId().equals(parentId)) {
                final String todoItem = todo.getLabel();

                final TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                final CheckBox checkBox = new CheckBox(this);
                final TextView todoView = new TextView(this);
                final ImageView closeIcon = new ImageView(this);

                tableRow.addView(checkBox);
                todoView.setText(todoItem);
                tableRow.addView(todoView);

                closeIcon.setImageResource(R.drawable.baseline_close_24);
                closeIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        tableLayout.removeView(tableRow);
                    }
                });
                tableRow.addView(closeIcon);
                tableLayout.addView(tableRow);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        if (isChecked) {
                            todoView.setTextColor(ContextCompat.getColor(ProjectActivity.this, android.R.color.darker_gray));
                            todo.setChecked();
                        } else {
                            todoView.setTextColor(Color.BLACK);
                        }
                    }
                });

                editText.getText().clear();
            }
        }
    }
}