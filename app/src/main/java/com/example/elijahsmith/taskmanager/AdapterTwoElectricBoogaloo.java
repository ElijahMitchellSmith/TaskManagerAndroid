package com.example.elijahsmith.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterTwoElectricBoogaloo extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Task> taskList;
    private List<Task> incompleteTasks;
    private List<Task> completeTasks;
    private Adapter.AdapterCallback adapterCallback;

    public AdapterTwoElectricBoogaloo(List<Task> taskList, Adapter.AdapterCallback adapterCallback) {
        this.completeTasks= taskList;
        this.adapterCallback = adapterCallback;

    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);


        return new Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.bindTask(completeTasks.get(position));

        //TODO - Set up onClicks for deleting and checking out
        holder.itemView.setOnClickListener(holder.onClick(completeTasks.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(completeTasks.get(position)));

    }

    @Override
    public int getItemCount() {

        return completeTasks.size();
    }

    public void updateList(List<Task> list) {
        completeTasks = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TODO - Create ViewHolder Variables and methods
        @BindView(R.id.item_layout)
        protected ConstraintLayout rowlayout;
        @BindView(R.id.task_title)
        protected TextView taskTitle;
        //  @BindView(R.id.item_genre)
        //  protected TextView genre;
        @BindView(R.id.date_text)
        protected TextView taskDate;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        public View.OnClickListener onClick(final Task task) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.rowClicked(task);
                }
            };
        }

        public View.OnLongClickListener onLongClick(final Task task) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallback.rowLongClicked(task);
                    return true;
                }

            };
        }


        public void bindTask(Task task) {

            taskTitle.setText(task.getTaskTitle());

            if (task.isComplete()) {
                //make due date visible
                taskDate.setVisibility(View.VISIBLE);
                //show day game was checked out
                task.setDate(taskDate.getText().toString());
                //change background color
                rowlayout.setBackgroundResource(R.color.red);
                //calculate check in date


            } else {
                taskDate.setVisibility(View.INVISIBLE);
                rowlayout.setBackgroundResource(R.color.green);
            }

        }
    }


   public interface AdapterCallback {
       //TODO:Create callback methods needed
        Context getContext();

        void rowClicked(Task task);


        void rowLongClicked(Task task);
    }
}

