package com.hsappdev.ahs.ui.reusable.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsappdev.ahs.newCache.DataType;

/**
 * View holder for AbstractDataRecyclerView
 * @param <T> type extends DataType
 */
public class AbstractDataViewHolder<T extends DataType> extends RecyclerView.ViewHolder{

    private DataTypeViewAdapter<T> dataAdapter;

    public AbstractDataViewHolder(@NonNull View itemView, DataTypeViewAdapter<T> adapter) {
        super(itemView);

        this.dataAdapter = adapter;

    }

    private void init(T t) {
        itemView.setOnClickListener(view -> dataAdapter.handleOnClick(t, view));
    }


    public void setData(T t) {
        init(t);
        dataAdapter.setDataToView(t, itemView);
    }
}
