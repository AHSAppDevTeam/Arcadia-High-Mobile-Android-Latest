package com.hsappdev.ahs.newDataTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleData {

    private String iconURL;
    private String color;
    private String title;

    private List<PeriodData> organizedData;

    private int dots;

    private List<String> periodIDs = new ArrayList<>();
    private List<Integer> timestamps = new ArrayList<>();

    @Override
    public String toString() {
        return "ScheduleData{" +
                "iconURL='" + iconURL + '\'' +
                ", color='" + color + '\'' +
                ", title='" + title + '\'' +
                ", dots=" + dots +
                ", periodIDs=" + periodIDs +
                ", timestamps=" + timestamps +
                '}';
    }

    private void organizeData(){
        if(organizedData != null) organizedData.clear(); else organizedData = new ArrayList<>();

        for(int i = 0; i < timestamps.size(); i++) {
            int j = i+1;
            if(j < timestamps.size()) {
                if(i>=periodIDs.size()){
                    return; // why return; instead of continue;? because we've reached the limit for number of labels for the timestamps
                }
                PeriodData periodData = new PeriodData(timestamps.get(i), timestamps.get(j), periodIDs.get(i));

                organizedData.add(periodData);
            }
        }
    }

    // GETTERS AND SETTERS

    public int getEarliestTimeStamp() {
        if(!timestamps.isEmpty()){
            return timestamps.get(0);
        }
        return 0;
    }

    public int getLatestTimeStamp() {
        if(!timestamps.isEmpty()){
            return timestamps.get(timestamps.size()-1);
        }
        return 0;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDots() {
        return dots;
    }

    public void setDots(int dots) {
        this.dots = dots;
    }

    public List<String> getPeriodIDs() {
        return periodIDs;
    }

    public void setPeriodIDs(List<String> periodIDs) {
        this.periodIDs = periodIDs;
    }

    public List<Integer> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<Integer> timestamps) {
        this.timestamps = timestamps;
    }

    public List<PeriodData> getOrganizedData() {
        organizeData();
        return organizedData;
    }

    public void setOrganizedData(List<PeriodData> organizedData) {
        this.organizedData = organizedData;
    }
}
