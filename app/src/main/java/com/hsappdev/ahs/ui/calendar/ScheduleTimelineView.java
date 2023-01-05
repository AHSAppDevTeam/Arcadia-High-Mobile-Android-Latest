package com.hsappdev.ahs.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Renders a timeline that has a fixed interval of 1 hour
 */
public class ScheduleTimelineView extends LinearLayout {

    private static final String TAG = "ScheduleTimelineView";

    private int startTime, endTime;
    private int viewHeightPx;

    public ScheduleTimelineView(Context context) {
        super(context);

        init();
    }

    public ScheduleTimelineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScheduleTimelineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ScheduleTimelineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
//        setBackgroundColor(Color.RED);
//        for (int i = 0; i <10; i++) {
//            TextView textView = new TextView(getContext());
//            textView.setText("I lauv you!");
//            addView(textView);
//        }
    }

    public void render(int startTime, int endTime, int viewHeightPx) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.viewHeightPx = viewHeightPx;

        // create views
        int firstBlockTimeDuration = 60 - startTime % 60;
        int firstIntervalTime = startTime + firstBlockTimeDuration;
        int lastBlockTimeDuration = startTime % 60;
        int lastIntervalTime = endTime - lastBlockTimeDuration;

        int numberOfBlocks = (lastIntervalTime-firstIntervalTime)/60; // truncated to int (should always be int anyway)
        float oneHourBlockSizePx = ((float) viewHeightPx) / ((endTime-startTime) / 60f);

        Log.d(TAG, viewHeightPx+"");

        int counter = firstIntervalTime;

        addView(getTimeBlockView((firstBlockTimeDuration/60f)*oneHourBlockSizePx, counter));

        for (int i = 0; i < numberOfBlocks; i++) {
            counter += 60;
            if(lastIntervalTime != counter) {
                addView(getTimeBlockView(oneHourBlockSizePx, counter));
            }
        }


    }

    public TextView getTimeBlockView(float heightPx, int timeStamp) {
        TextView block = new TextView(getContext());
        block.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        block.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        block.setText(formatMinutesAfterMidnightToHourString(timeStamp));
        block.setHeight((int) heightPx);
        return block;
    }

    private String formatMinutesAfterMidnightToHourString(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, minutes/60);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ha", Locale.US);
        return dateFormat.format(calendar.getTime()).toLowerCase(Locale.US);
    }


}
