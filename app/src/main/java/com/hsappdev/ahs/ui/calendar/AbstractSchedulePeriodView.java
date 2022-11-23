package com.hsappdev.ahs.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hsappdev.ahs.R;
import com.hsappdev.ahs.newDataTypes.PeriodData;

public abstract class AbstractSchedulePeriodView extends ConstraintLayout {

    private static final int PERIOD_SCALING_FACTOR = 3;
    private static final int PASSING_PERIOD_DEFAULT_DURATION = 7;

    protected PeriodData periodData;
    protected View periodView;

    public AbstractSchedulePeriodView(Context context, PeriodData periodData) {
        super(context);
        this.periodData = periodData;
        onCreateView();
    }

    abstract public int getLayoutId();

    public AbstractSchedulePeriodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onCreateView();
    }

    protected void onCreateView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        periodView = layoutInflater.inflate(getLayoutId(), this, false);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, periodData.getDuration()*PERIOD_SCALING_FACTOR);


        if(periodData.getPeriodType() == PeriodData.PERIOD_TYPE.LUNCH_PERIOD) {
            CardView scheduleCardView = periodView.findViewById(R.id.scheduleCardView);
            int passingPeriodPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PASSING_PERIOD_DEFAULT_DURATION*PERIOD_SCALING_FACTOR, getResources().getDisplayMetrics());
            passingPeriodPadding = PASSING_PERIOD_DEFAULT_DURATION*PERIOD_SCALING_FACTOR;
            MarginLayoutParams lp = (MarginLayoutParams) scheduleCardView.getLayoutParams();
            lp.setMargins(0, passingPeriodPadding, 0, passingPeriodPadding);

            // bg
            ConstraintLayout bgLayout = periodView.findViewById(R.id.schedule_period_background_layout);
            bgLayout.setBackgroundResource(R.drawable.schedule_bright_yellow_gradient);

            // update period data - be aware of this
//            periodData.setStartTimestamp(periodData.getStartTimestamp()+7);
//            periodData.setEndTimestamp(periodData.getEndTimestamp()-7);

        }

        this.addView(periodView, params);
    }

}
