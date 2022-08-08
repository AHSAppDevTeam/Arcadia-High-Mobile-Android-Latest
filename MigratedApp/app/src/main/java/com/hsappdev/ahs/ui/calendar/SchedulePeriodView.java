package com.hsappdev.ahs.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModel;

import com.hsappdev.ahs.R;
import com.hsappdev.ahs.newDataTypes.PeriodData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SchedulePeriodView extends AbstractSchedulePeriodView {

    private TextView periodTitle;
    private TextView periodTimestamps;
    private TextView periodDuration;

    public SchedulePeriodView(Context context, PeriodData periodData) {
        super(context, periodData);
    }

    @Override
    public int getLayoutId() {
        return R.layout.schedule_period_section;
    }

    public SchedulePeriodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onCreateView(){
        super.onCreateView();

        // extract references
        periodTitle = periodView.findViewById(R.id.schedule_period_title);
        periodTimestamps = periodView.findViewById(R.id.schedule_period_timestamp);
        periodDuration = periodView.findViewById(R.id.schedule_period_duration);

        updateView();

    }

    private void updateView(){
        // set title
        periodTitle.setText(periodData.getPeriodTitle());
        int duration = periodData.getDuration();

        boolean isLunch = periodData.getPeriodType() == PeriodData.PERIOD_TYPE.LUNCH_PERIOD;

        if(isLunch) {
            // set text color
            periodDuration.setTextColor(getResources().getColor(R.color.schedule_bright_yellow, getContext().getTheme()));
            duration-=7;
        }
        periodDuration.setText(String.format("%d\nmin%s", duration, duration != 1 ? "s" : ""));



        String startTimeString = formatMinutesAfterMidnightToString(periodData.getStartTimestamp()),
                endTimeString = formatMinutesAfterMidnightToString(periodData.getEndTimestamp() + (isLunch ? -7 : 0));

        periodTimestamps.setText(String.format("%s - %s",  startTimeString, endTimeString));
    }

    private String formatMinutesAfterMidnightToString(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, minutes/60);
        calendar.set(Calendar.MINUTE, minutes % 60);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        return dateFormat.format(calendar.getTime());
    }

}
