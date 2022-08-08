package com.hsappdev.ahs.ui.calendar;


import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.hsappdev.ahs.newDataTypes.DayData;
import com.hsappdev.ahs.newDataTypes.ScheduleData;
import com.hsappdev.ahs.newDataTypes.WeekData;
import com.hsappdev.ahs.viewModels.ScheduleViewModel;
import com.kizitonwose.calendarview.CalendarView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.HashMap;

public class CalendarState {

    private static final String TAG = "CalendarState";

    private LocalDate selectedDate = null;
    private CalendarView calendarView;

    private static final int WEEKS_IN_YEAR = 53;
    private static final int DAYS_IN_WEEK = 7;

    private ScheduleViewModel viewModel;

    private HashMap<Integer, WeekData> weeks;

    public CalendarState(ScheduleViewModel viewModel) {
        this.viewModel = viewModel;
    }

//    /* Not needed ???
    public void loadCalendarDataAsync(ScheduleViewModel viewModel, FragmentActivity activity){
        weeks = viewModel.getCalendarData();

        for (int weekOfYear = 1; weekOfYear <= WEEKS_IN_YEAR; weekOfYear++) {
            WeekData week = weeks.get(weekOfYear);
            if (week != null) {
                for (int dayOfWeek = 0; dayOfWeek < DAYS_IN_WEEK; dayOfWeek++) {
                    int finalWeekOfYear = weekOfYear;
                    int finalDayOfWeek = dayOfWeek;

                    // formulate the date
                    LocalDate targetDate = LocalDate.now()
                            .with(WeekFields.ISO.weekOfWeekBasedYear(), finalWeekOfYear) // week of year
                            .with(WeekFields.ISO.dayOfWeek(), finalDayOfWeek+1); // day of week

                    if(week.getDayList().get(dayOfWeek) != null) {
                        week.getDayList().get(dayOfWeek).observe(activity, dayData -> dayData.getSchedule().observe(activity, scheduleData -> {
                            Log.d(TAG, targetDate.toString());
                            calendarView.notifyDateChanged(targetDate);
                            // invokes updateView within DayViewContainer
                            // which calls getDayData to get the actual data
                            // because the data can't be passed directly
                            // this ensures data is fully loaded before the method is called

                            Log.d(TAG, String.format("week: %d, day: %d, : %s", finalWeekOfYear, finalDayOfWeek, dayData.getScheduleId()));
                            Log.d(TAG, scheduleData.toString());
                        }));
                    } else {
                        Log.d(TAG, String.format("null at week: %d, day: %d", finalWeekOfYear, finalDayOfWeek));

                    }
                }

            }
        }
    }


// */


    // GETTERS AND SETTERS
    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public DayData getSpecificDayDataValue(int weekOfYear, int dayOfWeek) {
        WeekData week = weeks.get(weekOfYear);
        return week.getDayList().get(dayOfWeek).getValue();
    }
}
