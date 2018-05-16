package com.example.elijahsmith.taskmanager;

import android.app.Application;
import android.arch.persistence.room.Room;

public class TaskApplication extends Application {
    private TaskDatabase database;
    private TaskDatabaseTwoElectricBoogaloo databaseTwo;
    public static String DATABASE_NAME = "task_database";
    public static String DATABASE_TWO_NAME = "task_database_two";

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        databaseTwo = Room.databaseBuilder(getApplicationContext(), TaskDatabaseTwoElectricBoogaloo.class, DATABASE_TWO_NAME).allowMainThreadQueries().build();

    }
    public TaskDatabase getDatabase() {
        return database;
    }
    public TaskDatabaseTwoElectricBoogaloo getDatabaseTwo() {
        return databaseTwo;
    }
}

