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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
    private DrawerLayout drawerLayout;
    private ArrayAdapter<Todo> todoAdapter;
    private List<Todo> searchTodos;
    private String spinnerStatus = "status";

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
        drawerLayout = findViewById(R.id.drawerLayoutTodo);
        menuButton = findViewById(R.id.menuButton1);
        final Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");
        final String projectName = intent.getStringExtra("projectName");
        viewTodoList(projectId);
        searchButton = findViewById(R.id.search_ButtonTodo);
        final ImageButton addTodo = findViewById(R.id.add_todo_search);
        final ImageButton searchMenuButton = findViewById(R.id.menu_search);
        final Spinner statusSpinner = findViewById(R.id.search_bar_status);
        final SearchView searchView = findViewById(R.id.search_bar);
        final List<String> todoStatuses = new ArrayList<>();
        searchTodos = new ArrayList<>();
        final ListView todosView = findViewById(R.id.todoSearchItems);

        todoStatuses.add("status");
        todoStatuses.add("checked");
        todoStatuses.add("unchecked");
        final ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, todoStatuses);
        todoAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchTodos);

        statusSpinner.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();
        todosView.setAdapter(todoAdapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                switch (position) {
                    case 1:
                        spinnerStatus = "status";
                        break;
                    case 2:
                        spinnerStatus = "checked";
                        break;
                    case 3:
                        spinnerStatus = "unChecked";
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
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        searchMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String searchElement) {
                searchTodos.clear();
                getSearchItem(searchElement);
                todoAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String searchElement) {
                searchTodos.clear();
                getSearchItem(searchElement);
                todoAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    public void getSearchItem(final String searchElement) {
        if (spinnerStatus.equals("checked")) {
            for (final Todo todo : todos) {
                if (todo.getLabel().equals(searchElement)) {
                    if (todo.isChecked()) {
                        searchTodos.add(todo);
                    }
                }
            }
        } else if (spinnerStatus.equals("unChecked")) {
            for (final Todo todo : todos) {
                if (todo.getLabel().equals(searchElement)) {
                    if (! todo.isChecked()) {
                        searchTodos.add(todo);
                    }
                }
            }
        } else {
            for (final Todo todo : todos) {
                if (todo.getLabel().equals(searchElement)) {
                    searchTodos.add(todo);
                }
            }
        }
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
            Toast.makeText(getApplicationContext(),"Enter todo",Toast.LENGTH_LONG).show();
        } else {
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
                        if (isChecked) {
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
    }

    public void removeTodo(final String parentId) {
        for (final Todo todo: todos) {
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