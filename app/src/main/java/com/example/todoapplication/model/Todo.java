package com.example.todoapplication.model;

import androidx.annotation.NonNull;

/**
 * <p>
 * Represents an Todo
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class Todo {
    private String id;
    private String label;
    private String parentId;
    private boolean isChecked;

    public Todo(final String id, final String label, final boolean isChecked, final String parentId) {
        this.id = id;
        this.label = label;
        this.isChecked = isChecked;
        this.parentId = parentId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked() {
        this.isChecked = !this.isChecked;
    }

    public String getTodo() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("id - ");
        buffer.append(id);
        buffer.append("  label-");
        buffer.append(label);
        buffer.append("   checked -");
        buffer.append(isChecked);
        return buffer.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return getTodo();
    }
}