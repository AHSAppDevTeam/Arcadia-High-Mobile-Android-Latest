package com.hsappdev.ahs.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        this.clearScheduleView();
        if(scheduleData == null) return;
        Log.d(TAG, "render schedule");

        setOrientation(LinearLayout.VERTICAL);

        renderTitle();
        renderMain();

    }

    private void renderTitle() {
        View titleView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_header, null);
        TextView title = titleView.findViewById(R.id.schedule_header_title);
        TextView day = titleView.findViewById(R.id.schedule_header_day);
        TextView date = titleView.findViewById(R.id.schedule_header_date);

        String dateText = scheduleDate.format(new DateTimeFormatterBuilder().appendPattern("MMMM d").toFormatter());
        String dayText = scheduleDate.format(new DateTimeFormatterBuilder().appendPattern("EEEE").toFormatter());

        if(currentScheduleData.getTitle() != null) {
            title.setText(currentScheduleData.getTitle());
            day.setText(dayText);
            date.setText(dateText);
        }

        this.addView(titleView);
    }

    /**
     * Renders the schedule blocks
     */
    private void renderMain(){

        // add timeline by using a linear layout divided by a ratio horizontally
        LinearLayout container = new LinearLayout(getContext());

        LinearLayout scheduleTimelineView = new ScheduleTimelineView(getContext()); // the left side
        LinearLayout scheduleBlock = new LinearLayout(getContext()); // the right side
        scheduleBlock.setOrientation(LinearLayout.VERTICAL);

        // set layout params
        LinearLayout.LayoutParams timelineParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, .1f);
        LinearLayout.LayoutParams scheduleParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, .9f);


        scheduleTimelineView.setLayoutParams(timelineParams);
        scheduleBlock.setLayoutParams(scheduleParams);

        container.addView(scheduleTimelineView);
        container.addView(scheduleBlock);

        addView(container);



        List<PeriodData> organizedData = currentScheduleData.getOrganizedData();
        for(int i = 0; i < organizedData.size(); i++){
            PeriodData period = organizedData.get(i);

            AbstractSchedulePeriodView periodView =
                    (period.getPeriodType() == PeriodData.PERIOD_TYPE.PASSING_PERIOD) ?
                            new PassingPeriodView(getContext(), period) : new SchedulePeriodView(getContext(), period);
            scheduleBlock.addView(periodView);
        }
    }

    public void clearScheduleView() {
        this.removeAllViews();
    }
}
