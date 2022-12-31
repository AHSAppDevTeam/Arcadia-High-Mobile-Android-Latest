package com.hsappdev.ahs.ui.calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsappdev.ahs.R;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.ui.ViewContainer;

import java.time.format.TextStyle;
import java.util.Locale;

public class MonthHeaderContainer extends ViewContainer {
    private TextView monthText;
    private TextView yearText;
    public MonthHeaderContainer(@NonNull View view) {
        super(view);
        monthText = view.findViewById(R.id.calendarMonthText);
        yearText = view.findViewById(R.id.calendarMonthYearText);
    }

    public void updateView(CalendarMonth calendarDay) {
        setMonthText(calendarDay.getYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.US));
        setYearText(calendarDay.getYear());
    }

    private void setYearText(int year) {
        yearText.setText(Integer.toString(year));
    }

    private void setMonthText(String displayName) {
        monthText.setText(displayName);
    }
}
