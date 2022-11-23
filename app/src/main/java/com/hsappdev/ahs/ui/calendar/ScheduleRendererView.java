package com.hsappdev.ahs.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hsappdev.ahs.R;
import com.hsappdev.ahs.newDataTypes.PeriodData;
import com.hsappdev.ahs.newDataTypes.ScheduleData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Custom view for rendering a schedule :)
 */
public class ScheduleRendererView extends LinearLayout {

    private static final String TAG = "ScheduleRendererView";

    private ScheduleData currentScheduleData;
    private LocalDate scheduleDate;

    public ScheduleRendererView(Context context) {
        super(context);
    }

    public ScheduleRendererView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void renderScheduleViewWithData(ScheduleData scheduleData, LocalDate scheduleDate) {
        this.currentScheduleData = scheduleData;
        this.scheduleDate = scheduleDate;

        // set padding
        int p = 10;
        this.setPadding(0, p, 0, p);

        this.clearScheduleView();
        if(scheduleData == null) return;
        Log.d(TAG, "render schedule");
//        TextView title = new TextView(getContext());
//        title.setText(scheduleData.getTitle() == null ? "Schedule" : scheduleData.getTitle());
//        this.addView(title);

        renderTitle();
        renderMain();

//        SchedulePeriodView schedulePeriodView = new SchedulePeriodView(getContext());
//        this.addView(schedulePeriodView);
//        SchedulePeriodView schedulePeriodView1 = new SchedulePeriodView(getContext());
//        this.addView(schedulePeriodView1);
    }

    private void renderTitle() {
        View titleView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_header, null);
        TextView title = titleView.findViewById(R.id.schedule_header_title);
        TextView day = titleView.findViewById(R.id.schedule_header_day);
        TextView date = titleView.findViewById(R.id.schedule_header_date);

        String dateText = scheduleDate.format(new DateTimeFormatterBuilder().appendPattern("MMMM d").toFormatter());
        String dayText = scheduleDate.format(new DateTimeFormatterBuilder().appendPattern("EEEE").toFormatter());


        title.setText(currentScheduleData.getTitle());
        day.setText(dayText);
        date.setText(dateText);

        this.addView(titleView);
    }

    private void renderMain(){
        List<PeriodData> organizedData = currentScheduleData.getOrganizedData();
        for(int i = 0; i < organizedData.size(); i++){
            PeriodData period = organizedData.get(i);

            AbstractSchedulePeriodView periodView =
                    (period.getPeriodType() == PeriodData.PERIOD_TYPE.PASSING_PERIOD) ?
                            new PassingPeriodView(getContext(), period) : new SchedulePeriodView(getContext(), period);
            this.addView(periodView);
        }
    }

    public void clearScheduleView() {
        this.removeAllViews();
    }
}
