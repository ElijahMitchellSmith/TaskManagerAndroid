package com.example.elijahsmith.taskmanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.elijahsmith.taskmanager.DateConverter;
import com.example.elijahsmith.taskmanager.Task;
import com.example.elijahsmith.taskmanager.TaskDao;

@Database(version = 1, entities = Task.class)
@TypeConverters(DateConverter.class)

public abstract class TaskDatabaseTwoElectricBoogaloo extends RoomDatabase {

    public abstract TaskDao taskDao();
}