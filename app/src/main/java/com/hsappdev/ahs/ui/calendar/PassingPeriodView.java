package com.hsappdev.ahs.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.hsappdev.ahs.R;
import com.hsappdev.ahs.newDataTypes.PeriodData;

public class PassingPeriodView extends AbstractSchedulePeriodView{
    public PassingPeriodView(Context context, PeriodData periodData) {
        super(context, periodData);
    }

    @Override
    public int getLayoutId() {
        return R.layout.schedule_passing_period_section;
    }

    public PassingPeriodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreateView() {
       super.onCreateView();
    }
}
