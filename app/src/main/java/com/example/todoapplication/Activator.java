package com.example.todoapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todoapplication.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Represents the Activator of the Application
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class Activator extends AppCompatActivity {

    public static List<Project> projectList;
    private DrawerLayout drawerLayout;
    private Button removeButton;
    private static ArrayAdapter<Project> arrayAdapter;
    private static Long projectId;
    private static ProjectActivity projectActivity;

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
        setContentView(R.layout.activity_main);
        ImageView backButton = findViewById(R.id.backButton);
        Button addButton = findViewById(R.id.createList);
        drawerLayout = findViewById(R.id.Layout);
        ImageButton menuButton = findViewById(R.id.menuButton);
        ListView listView = findViewById(R.id.nameListView);
        projectList = new ArrayList<>();
        projectId = 1L;
        projectActivity = new ProjectActivity();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, projectList);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                obtainName();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                onClickProject(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeProject(i);
                return true;
            }
        });
    }

    public void onClickProject(final int position) {
        final Project project = projectList.get(position);
        final Intent intent = new Intent(Activator.this, ProjectActivity.class);

        intent.putExtra("projectId", project.getId());
        intent.putExtra("projectName", project.getLabel());
        startActivity(intent);
    }

    /**
     * <p>
     * Called when the user presses the back button.
     * </p>
     */
    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * <p>
     * Obtains the user's name through user interaction
     * </p>
     */
    private void obtainName() {
        final EditText text = new EditText(this);
        text.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this).setTitle("Add Name").setView(text).setPositiveButton("Ok", (dialog, which) -> {
            final String name = text.getText().toString().trim();

            if (!name.isEmpty()) {
                final Project project = new Project(String.valueOf(projectId++), name);
                projectList.add(project);
                arrayAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Enter project name", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", null).create().show();
    }

    /**
     * <p>
     *     Represent removal of project from projectList
     * </p>
     * @param position Represents the position of project in projectList
     */
    public void removeProject(final int position) {
        final Project project = projectList.get(position);
        projectList.remove(position);
        projectActivity.removeTodo(project.getId());
        arrayAdapter.notifyDataSetChanged();
    }


}