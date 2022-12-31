package com.hsappdev.ahs.newCache;

import android.view.View;

import androidx.room.Ignore;


public abstract class DataType implements Comparable<DataType> {

    /**
     * A unique string id for each data type
     */
    @Ignore
    public String dataId;


    /**
     * Hash of the contents inside this object (to check if contents are the same)
     */
    @Ignore
    public String dataHash = "";

    /**
     * Shows if this article has been loaded yet<br>
     * true = not loaded<br>
     * false = loaded
     */
    @Ignore
    public boolean isLoading = false;

    public DataType() {
    }

    public DataType(String dataId) {
        this.dataId = dataId;
    }


    // GETTERS AND SETTERS


    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
