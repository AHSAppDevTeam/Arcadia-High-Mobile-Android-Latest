package com.hsappdev.ahs.ui.calendar;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
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
        this.dayData = calendarState.getSpecificDayDataValue(getWeekOfYear(), getDayOfWeek());
        this.scheduleData = dayData.getSchedule().getValue();

        if(scheduleData == null) return;

        if(calendarState.getSelectedDate() == null && calendarDay.getDate().atStartOfDay().equals(LocalDate.now().atStartOfDay())) {
            // automatically open the current day schedule
            calendarState.setSelectedDate(calendarDay.getDate());
            scheduleRendererView.renderScheduleViewWithData(scheduleData, calendarDay.getDate());
        }

        toggleHighlight(isSelected());
        renderView();

    }

    private void renderView() {
        dateTextView.setText(String.format("%s", getScheduleDayOfMonth()));
        dotsTextView.setText(scheduleData.getDotsString());
    }

    private boolean isWithinMonthRange(){
        return calendarDay.getOwner() == DayOwner.THIS_MONTH;
    }

    private boolean isSelected() {
        return !(calendarState.getSelectedDate() == null || !calendarState.getSelectedDate().equals(calendarDay.getDate()));
    }


    public void toggleHighlight(Boolean highlight) {
        int highlightedBackgroundColor = scheduleData != null ? scheduleData.getColorInt() : Color.BLUE;
        int highlightedTextColor = Color.WHITE;

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = dateTextView.getContext().getTheme();

        theme.resolveAttribute(com.google.android.material.R.attr.backgroundColor, typedValue, true);
        int defaultBackgroundColor = typedValue.data;

        theme.resolveAttribute(R.attr.titleColor, typedValue, true);
        int defaultTextColor = typedValue.data;

        // refactor later for dark theme
        //theme.resolveAttribute(R.attr.mutedTitleColor, typedValue, true);
        int defaultMutedTextColor = Color.GRAY;

        if(isToday() || highlight) {
            view.setBackgroundColor(highlightedBackgroundColor);
            dateTextView.setTextColor(highlightedTextColor);
            dotsTextView.setTextColor(highlightedTextColor);
        } else {
            view.setBackgroundColor(defaultBackgroundColor);
            if(calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                dateTextView.setTextColor(defaultTextColor);
            } else {
                dateTextView.setTextColor(defaultMutedTextColor);
            }
            dotsTextView.setTextColor(highlightedBackgroundColor);
        }
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

    private int getCurrentDayOfMonth() {
        return LocalDate.now().getDayOfMonth();
    }

    private int getScheduleDayOfMonth() {
        return calendarDay.getDay();
    }

    private boolean isToday() {
        if(calendarDay.getOwner() == DayOwner.THIS_MONTH) {
            if (calendarDay.getDay() == getCurrentDayOfMonth()) {
                if (calendarDay.getDate().atStartOfDay().equals(LocalDate.now().atStartOfDay())) {
                    return true;
                }
            }
        }
        return false;
    }


}
