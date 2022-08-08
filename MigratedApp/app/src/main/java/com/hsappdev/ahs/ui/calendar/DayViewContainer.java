package com.hsappdev.ahs.ui.calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsappdev.ahs.R;
import com.hsappdev.ahs.newDataTypes.DayData;
import com.hsappdev.ahs.newDataTypes.ScheduleData;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.ViewContainer;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

public class DayViewContainer extends ViewContainer implements View.OnClickListener {

    private CalendarDay calendarDay;
    private CalendarState calendarState;

    private DayData dayData;
    private ScheduleData scheduleData;

    private ScheduleRendererView scheduleRendererView;


    private View view;
    private TextView dateTextView;
    private TextView dotsTextView;



    public DayViewContainer(@NonNull View view, CalendarState calendarState, ScheduleRendererView scheduleRendererView) {
        super(view);
        this.calendarState = calendarState;
        this.scheduleRendererView = scheduleRendererView;
        this.view = view;
        this.dateTextView = view.findViewById(R.id.calendarDayText);
        this.dotsTextView = view.findViewById(R.id.calendarDayDots);
        view.setOnClickListener(this);
    }

    public void updateView(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;

        if(!isWithinMonthRange()) {
            view.setVisibility(View.INVISIBLE);
            return;
        }
        view.setVisibility(View.VISIBLE);

        // update with firebase data
        dayData = calendarState.getSpecificDayDataValue(getWeekOfYear(), getDayOfWeek());
        updateViewWithData(dayData);

        if(calendarState.getSelectedDate() == null && calendarDay.getDate().atStartOfDay().equals(LocalDate.now().atStartOfDay())) {
            // automatically open the current day schedule
            calendarState.setSelectedDate(calendarDay.getDate());
            scheduleRendererView.renderScheduleViewWithData(scheduleData, calendarDay.getDate());
        }

        if(isSelected()) {
            dateTextView.setText("NS");
        } else {
            dateTextView.setText("S");
        }
    }

    private boolean isWithinMonthRange(){
        return calendarDay.getOwner() == DayOwner.THIS_MONTH;
    }

    private boolean isSelected() {
        return calendarState.getSelectedDate() == null || !calendarState.getSelectedDate().equals(calendarDay.getDate());
    }

    private void updateViewWithData(DayData newDayData) {
        this.dayData = newDayData;
        this.scheduleData = dayData.getSchedule().getValue();

        dotsTextView.setText(Integer.toString(scheduleData.getDots()));
    }

    @Override
    public void onClick(View view) {
        if(!isWithinMonthRange()) return;

        // Only use month dates
        LocalDate currentSelection = calendarState.getSelectedDate();
        if(currentSelection != null && currentSelection.isEqual(calendarDay.getDate())){
            // de-select
            calendarState.setSelectedDate(null);
        } else {
            calendarState.setSelectedDate(calendarDay.getDate());
            if (currentSelection != null) {
                calendarState.getCalendarView().notifyDateChanged(currentSelection);
            }

        }
        calendarState.getCalendarView().notifyDateChanged(calendarDay.getDate());

        if(calendarState.getSelectedDate() == null) {
            scheduleRendererView.clearScheduleView();
        } else {
            scheduleRendererView.renderScheduleViewWithData(scheduleData, calendarDay.getDate());
        }

    }

    private int getDayOfWeek(){
        return calendarDay.getDate().get(WeekFields.ISO.dayOfWeek());
    }

    private int getWeekOfYear(){
        return calendarDay.getDate().get(WeekFields.ISO.weekOfWeekBasedYear());
    }



}
