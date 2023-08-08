package com.example.todoapplication.model;

import androidx.annotation.NonNull;

public class Project {

    private String id;
    private String label;
    private boolean checked;

    public Project(final String id, final String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = !this.checked;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "  "+label;
    }
}