package com.example.elijahsmith.taskmanager;

import android.view.ViewGroup;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elijahsmith.taskmanager.R;
import com.example.elijahsmith.taskmanager.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

import javax.security.auth.callback.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Task> taskList;
    private AdapterCallback adapterCallback;

    public Adapter(List<Task> taskList, AdapterCallback adapterCallback) {
        this.taskList= taskList;
        this.adapterCallback = adapterCallback;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTask(taskList.get(position));

        //TODO - Set up onClicks for deleting and checking out
        holder.itemView.setOnClickListener(holder.onClick(taskList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(taskList.get(position)));

    }

    @Override
    public int getItemCount() {

        return taskList.size();
    }

    public void updateList(List<Task> list) {
        taskList = list;
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
         //   genre.setText(adapterCallback.getContext().getString(R.string.game_genre, game.getGameGenre()));

            if (task.isComplete()) {
                //make due date visible
                taskDate.setVisibility(View.VISIBLE);
                //show day game was checked out
                task.setDate(new Date());
                //change background color
                rowlayout.setBackgroundResource(R.color.red);
                //calculate check in date
                int numberOfDays = 14;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(task.getDate());
                calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
                Date date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                taskDate.setText(adapterCallback.getContext().getString(R.string.due_date, formatter.format(date)));

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

