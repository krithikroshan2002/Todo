package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private DrawerLayout todoDrawerLayout;
    private NavigationView navigationView;
    Button todoButton;
    AlertDialog todoDialog;
    LinearLayout todoLayout;
    private ImageButton menuButton;
    private Button createButton;
    private ImageButton backButton;
    private LinearLayout layout;
    private AlertDialog dialog;
    private CardView cart;
    private static Integer childId = 1;
    private static Integer parentId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        menuButton = findViewById(R.id.menuButton);
        createButton = findViewById(R.id.createButton);
        backButton = findViewById(R.id.backButton);
        layout = findViewById(R.id.container);
        final View todoView = getLayoutInflater().inflate(R.layout.todo, null);

        Button todoButton = todoView.findViewById(R.id.createTodo);
        todoLayout = todoView.findViewById(R.id.container);
        todoDrawerLayout = todoView.findViewById(R.id.todo_drawer_layout);
        buildTodoDialog();
        buildDialog();

        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoDialog.show();
                todoDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        final EditText name = view.findViewById(R.id.nameEdit);
        final EditText description = view.findViewById(R.id.description);

        builder.setView(view);
        builder.setTitle("Enter Project Title")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createProject(name.getText().toString(),description.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }

    private void createProject(final String name, final String description) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.name);
        EditText descriptionView = view.findViewById(R.id.cartDescription);
        Button delete = view.findViewById(R.id.delete);
        Button viewButton = view.findViewById(R.id.viewButton);
        cart = view.findViewById(R.id.cart);
        final String id = parentId.toString();
        cart.setTag(id);

        nameView.setText(name);
        descriptionView.setText(description);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        layout.addView(view);
    }

    private void buildTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        final EditText name = view.findViewById(R.id.nameEdit);

        builder.setView(view);
        builder.setTitle("Enter Todo")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createTodo(name.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        todoDialog = builder.create();
    }

    private void createTodo(final String name) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);

        nameView.setText(name);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoLayout.removeView(view);
            }
        });

        todoLayout.addView(view);
    }
}