package com.example.elijahsmith.taskmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Task> taskList;
    private AdapterCallback adapterCallback;

    public Adapter(List<Task> taskList, AdapterCallback adapterCallback) {
        this.taskList = taskList;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.id.item_layout, parent, false);
        return new ViewHolder(view);
    }
}
