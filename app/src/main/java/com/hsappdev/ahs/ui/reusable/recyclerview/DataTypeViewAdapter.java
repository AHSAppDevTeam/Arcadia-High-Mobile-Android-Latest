package com.hsappdev.ahs.ui.reusable.recyclerview;

import android.view.View;

import com.hsappdev.ahs.newCache.DataType;

public interface DataTypeViewAdapter<T extends DataType> {

    /**
     * Set data to the view <br>
     * Handles the displaying of data
     * @param data
     * @param itemView
     */
    void setDataToView(T data, View itemView);

    /**
     * Method is called when the thing is clicked
     * @param data
     * @param itemView
     */
    void handleOnClick(T data, View itemView);


}
