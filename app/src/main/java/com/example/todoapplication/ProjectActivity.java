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

/**
 * <p>
 * Represents an activity associated with a project
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class ProjectActivity extends AppCompatActivity {

    private ImageButton menuButton;
    private EditText todo;
    private static List<Todo> todos = new ArrayList<>();
    ;
    private TextView name;
    private static Long todoId = 1L;

    /**
     * <p>
     * Called when the activity is first created
     * </p>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project);
        menuButton = findViewById(R.id.menuButton1);
        final String projectName = getIntent().getStringExtra("projectName");
        viewTodoList(projectName);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });

        todo = findViewById(R.id.todoEditText);
        name = findViewById(R.id.projectName);

        name.setText(projectName);
        final Button addButton = findViewById(R.id.button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createTodo(projectName);
            }
        });

    }

    /**
     * <p>
     * Adds a new todo item under the specified parent.
     * </p>
     *
     * @param parentId The ID of the parent item under which the new todo will be added.
     */
    public void createTodo(final String parentId) {
        final String todoItem = todo.getText().toString();
        final Todo todo = new Todo(String.valueOf(todoId++), todoItem, false, parentId);

        todos.add(todo);

        if (todos != null) {
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
                    todos.remove(todo);
                }
            });

            tableRow.addView(closeIcon);
            tableLayout.addView(tableRow);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    if (! todo.isChecked()) {
                        todoView.setTextColor(ContextCompat.getColor(ProjectActivity.this, android.R.color.darker_gray));
                        todo.setChecked();
                    } else {
                        todoView.setTextColor(Color.BLACK);
                    }
                }
            });
            this.todo.getText().clear();
        }
    }

    /**
     * <p>
     * Displays the list of todo items under the specified parent.
     * </p>
     *
     * @param parentId The ID of the parent item whose todo list will be viewed.
     */
    public void viewTodoList(final String parentId) {
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

                if(todo.isChecked()) {
                    checkBox.setChecked(true);
                }

                tableRow.addView(checkBox);
                todoView.setText(todoItem);
                tableRow.addView(todoView);

                closeIcon.setImageResource(R.drawable.baseline_close_24);
                closeIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        tableLayout.removeView(tableRow);
                        todos.remove(todo);
                    }
                });
                tableRow.addView(closeIcon);
                tableLayout.addView(tableRow);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        if (todo.isChecked()) {
                            todoView.setTextColor(ContextCompat.getColor(ProjectActivity.this, android.R.color.darker_gray));
                            final int index = todos.indexOf(todo);
                            todo.setChecked();
                            final Todo todo1 = new Todo(todo.getId(), todo.getLabel(), todo.isChecked(), todo.getParentId());
                            todos.set(index, todo1);
                        } else {
                            todoView.setTextColor(Color.BLACK);
                        }
                    }
                });
            }
        }
    }
}