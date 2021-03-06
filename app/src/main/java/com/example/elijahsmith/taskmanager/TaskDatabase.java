package com.example.elijahsmith.taskmanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(version = 1, entities = Task.class)
@TypeConverters(DateConverter.class)

public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
