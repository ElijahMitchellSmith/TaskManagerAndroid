package com.example.elijahsmith.taskmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddTaskFragment.ActivityCallback, Adapter.AdapterCallback {
    private Task task;
    private AddTaskFragment addTaskFragment;
    private int position;
    private Adapter adapter;
    private AdapterTwoElectricBoogaloo adapterTwoElectricBoogaloo;
    private TaskDatabase taskDatabase;
    private TaskDatabaseTwoElectricBoogaloo taskDatabaseTwoElectricBoogaloo;
    private TaskDao taskDao;
    @BindView(R.id.incomplete_task_recyclerview)
    protected RecyclerView incompleteRecycler;
    @BindView(R.id.complete_tasks_recyclerview)
    protected RecyclerView completeRecycler;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.add_task_fab)
    protected AdapterTwoElectricBoogaloo.AdapterCallback adapterCallback;
    protected FloatingActionButton addButton;
    @BindView(R.id.date_text)
    protected TextView taskDate;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
        adapterTwoElectricBoogaloo.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        taskDatabase = ((TaskApplication) getApplicationContext()).getDatabase();
        taskDatabaseTwoElectricBoogaloo = ((TaskApplication) getApplicationContext()).getDatabaseTwo();

        setUpRecyclerView();
        setUpTwoElectricBoogaloo();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


     /*   if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.add_task_fab)
    public void addTask() {
        addTaskFragment = AddTaskFragment.newInstance();
        addTaskFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, addTaskFragment).commit();

    }


    @Override
    public void addClicked() {

            getSupportFragmentManager().beginTransaction().remove(addTaskFragment).commit();
            addButton.setVisibility(View.INVISIBLE);
            adapter.updateList(taskDatabase.taskDao().getTasks());
            adapterTwoElectricBoogaloo.updateList(taskDatabaseTwoElectricBoogaloo.taskDao().getTasks());


        }


    private void setUpRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new Adapter(taskDatabase.taskDao().getTasks(), this);
        int numberOfDays = 14;
//        Calendar calendar = Calendar.getInstance();
  //      calendar.setTime(task.getDate());
    //    calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
      //  Date date = calendar.getTime();
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        taskDate.setText(adapterCallback.getContext().getString(R.string.due_date));
        incompleteRecycler.setLayoutManager(linearLayoutManager);
        incompleteRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void rowClicked(Task task) {
        //in "turnery"(?) true is always first.
        if (task.isComplete()) {
            deleteTask(task);
        } else {
            completeTask(task);
        }
    }

    private void deleteTask(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete task?")
                .setMessage("Are you sure you would like to delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDatabase.taskDao().deleteTask(task);
                        adapter.updateList(taskDatabase.taskDao().getTasks());
                        adapterTwoElectricBoogaloo.updateList(taskDatabase.taskDao().getTasks());
                        Toast.makeText(MainActivity.this,"Task Deleted!",Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void rowLongClicked(Task task) {
        completeTask(task);

    }
    private void completeTask(final Task task) {
        //TODO - create Alert Dialog. Set title, message, positive button, negative button, and icon.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Complete Task?")
                .setMessage("Is this task complete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.setComplete(true);
                        task.getTaskTitle();
                        task.getDescription();
                        taskDatabaseTwoElectricBoogaloo.taskDao().addTask(task);
                        taskDatabase.taskDao().deleteTask(task);
                        taskDatabase.taskDao().updateTasks(task);
                        taskDatabaseTwoElectricBoogaloo.taskDao().updateTasks(task);
                        //let our adapter know that information in the database has changed to update our view accordingly
                        adapter.updateList(taskDatabase.taskDao().getTasks());
                        adapterTwoElectricBoogaloo.updateList(taskDatabaseTwoElectricBoogaloo.taskDao().getTasks());

                        Toast.makeText(MainActivity.this,"Task is completed!" ,Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    private void setUpTwoElectricBoogaloo() {
        LinearLayoutManager linearTwoElecricBoogaloo = new LinearLayoutManager(getApplicationContext());
        adapterTwoElectricBoogaloo = new AdapterTwoElectricBoogaloo(taskDatabaseTwoElectricBoogaloo.taskDao().getTasks(), this);
        completeRecycler.setLayoutManager(linearTwoElecricBoogaloo);
        completeRecycler.setAdapter(adapterTwoElectricBoogaloo);
        adapterTwoElectricBoogaloo.notifyDataSetChanged();

    }
}


//xmlns:app="http://schemas.android.com/apk/res-auto"
//    app:showAsAction="always" />

