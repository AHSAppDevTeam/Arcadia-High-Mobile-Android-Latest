package com.hsappdev.ahs.newDataTypes;

import com.hsappdev.ahs.util.Helper;

import java.util.logging.Handler;

public class PeriodData {

    public enum PERIOD_TYPE {
        PASSING_PERIOD,
        SPECIAL_PERIOD,
        CLASS_PERIOD,
        LUNCH_PERIOD
    }

    private int startTimestamp, endTimestamp;
    private String periodTitle;

    public PeriodData(int startTimestamp, int endTimestamp, String periodTitle) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.periodTitle = periodTitle;
    }

    public PERIOD_TYPE getPeriodType(){
        if(Helper.isStringAnInteger(periodTitle)){
            return PERIOD_TYPE.CLASS_PERIOD;
        } else if(periodTitle.equals("lunch")){
            return PERIOD_TYPE.LUNCH_PERIOD;
        } else if(periodTitle.equals("passing")) {
            return PERIOD_TYPE.PASSING_PERIOD;
        } else {
            return PERIOD_TYPE.SPECIAL_PERIOD;
        }
    }

    public int getDuration(){
        return getEndTimestamp()-getStartTimestamp();
    }

    public int getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(int startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public int getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(int endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getPeriodTitle() {
        return periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }
}
