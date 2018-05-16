package com.example.elijahsmith.taskmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTaskFragment extends Fragment {
    private ActivityCallback activityCallback;
    private TaskDatabase taskDatabase;
    private TaskDatabaseTwoElectricBoogaloo taskDatabaseTwoElectricBoogaloo;
    private TaskApplication taskApplication;
    @BindView(R.id.task_title_edit)
    protected EditText taskTitle;
    @BindView(R.id.task_description_edit)
    protected EditText taskDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public static AddTaskFragment newInstance() {

        Bundle args = new Bundle();

        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskDatabase = ((TaskApplication)getActivity().getApplicationContext()).getDatabase();
        taskDatabaseTwoElectricBoogaloo = ((TaskApplication)getActivity().getApplicationContext()).getDatabaseTwo();

    }
    @OnClick(R.id.add_task_button)
    protected void addButtonClicked() {
        if (taskTitle.getText().toString().isEmpty() || taskDescription.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "All Fields Are Required", Toast.LENGTH_LONG).show();
        } else {
            String title = taskTitle.getText().toString();
            String description = taskDescription.getText().toString();
            addTaskToDatabase(title, description);
            Toast.makeText(getActivity(), "Task Added!", Toast.LENGTH_LONG).show();

        }
    }
    private void addTaskToDatabase(final String title, final String description) {
        Task task = new Task(title,description,new Date());
        taskDatabase.taskDao().addTask(task);
        activityCallback.addClicked();




    }
    public void attachParent(ActivityCallback activityCallback) {
        this.activityCallback = activityCallback;

    }
    public interface ActivityCallback {

        void addClicked();

    }

}
