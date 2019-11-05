package com.clark.treemenu.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class TreeViewHolder extends RecyclerView.ViewHolder {
    public TreeViewHolder(@NonNull View itemView) {
        super(itemView);
        initViewHolder(itemView);
    }
    protected abstract void initViewHolder(View itemView);
}
