package com.hsappdev.ahs.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ScheduleTimelineView extends LinearLayout {

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
        setBackgroundColor(Color.RED);
        for (int i = 0; i <10; i++) {
            TextView textView = new TextView(getContext());
            textView.setText("I lauv you!");
            addView(textView);
        }
    }


}
