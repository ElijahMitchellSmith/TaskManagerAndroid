package com.example.elijahsmith.taskmanager;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private String taskTitle;
    private String description;
    private boolean isComplete;
    private Date date;

    public Task(String taskTitle, String description, Date date) {
        this.taskTitle = taskTitle;
        this.description = description;
        this.date = date;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

