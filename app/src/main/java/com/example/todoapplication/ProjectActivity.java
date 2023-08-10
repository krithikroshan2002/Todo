package com.example.todoapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    public static List<Todo> todos = new ArrayList<>();
    private TextView name;
    private static Long todoId = 1L;
    private ImageButton searchButton;
    private ImageButton addButton;
    private TableLayout tableLayout;


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
        tableLayout = findViewById(R.id.tableLayout);
        final Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");
        final String projectName = intent.getStringExtra("projectName");
        viewTodoList(projectId);
        searchButton = findViewById(R.id.search_ButtonTodo);
        addButton = findViewById(R.id.add_ButtonTodo);
        final Spinner statusSpinner = findViewById(R.id.search_bar_status);
        final SearchView searchView = findViewById(R.id.search_bar);
        final List<String> todoStatuses = new ArrayList<>();
        todoStatuses.add("status");
        todoStatuses.add("checked");
        todoStatuses.add("unchecked");
        final ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, todoStatuses);

        statusSpinner.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(ProjectActivity.this, ProjectActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("projectName", projectName);
                startActivity(intent);
            }
        });

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                switch (position) {
                    case 0:
                        tableLayout.removeAllViews();
                        for (final Todo todo : todos) {
                            addTodo(todo);
                        }
                        break;
                    case 1:
                        tableLayout.removeAllViews();
                        for (final Todo todo : todos) {
                            if (todo.isChecked()) {
                                addTodo(todo);
                            }
                        }
                        break;
                    case 2:
                        tableLayout.removeAllViews();
                        for (final Todo todo : todos) {
                            if (!todo.isChecked()) {
                                addTodo(todo);
                            }
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });

        todo = findViewById(R.id.todoEditText);
        name = findViewById(R.id.projectName);

        name.setText(projectName);
        final Button addButton = findViewById(R.id.createTodo);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createTodo(projectId);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchView.getVisibility() == view.GONE) {
                    searchView.setVisibility(view.VISIBLE);
                    statusSpinner.setVisibility(view.VISIBLE);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String searchElement) {
                tableLayout.removeAllViews();
                getSearchItem(searchElement);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String searchElement) {
                tableLayout.removeAllViews();
                getSearchItem(searchElement);
                return true;
            }
        });

    }

    public void getSearchItem(final String searchElement) {
        for (final Todo todo : todos) {
            if (todo.getLabel().equals(searchElement)) {
                addTodo(todo);
            }
        }
    }


    public void addTodo(final Todo todo) {
        final String todoItem = todo.getLabel();
        final TableRow tableRow = new TableRow(this);
        final CheckBox checkBox = new CheckBox(this);
        final TextView todoView = new TextView(this);
        final ImageView closeIcon = new ImageView(this);

        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        if (todo.isChecked()) {
            checkBox.setChecked(true);
            todoView.setTextColor(ContextCompat.getColor(ProjectActivity.this, android.R.color.darker_gray));
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
                if (isChecked) {
                    todoView.setTextColor(ContextCompat.getColor(ProjectActivity.this, android.R.color.darker_gray));
                    todo.setChecked();
                } else {
                    todoView.setTextColor(Color.BLACK);
                    todo.setChecked();
                }
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
        if (todoItem.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter todo", Toast.LENGTH_LONG).show();
        } else {
            final Todo todo = new Todo(String.valueOf(todoId++), todoItem, false, parentId);

            todos.add(todo);

            if (todos != null) {
                addTodo(todo);
                this.todo.getText().clear();
            }
        }
    }

    public void removeTodo(final String parentId) {
        for (final Todo todo : todos) {
            if (todo.getParentId().equals(parentId)) {
                todos.remove(todo);
            }
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

        for (final Todo todo : todos) {
            if (todo.getParentId().equals(parentId)) {
                addTodo(todo);
            }
        }
    }
}