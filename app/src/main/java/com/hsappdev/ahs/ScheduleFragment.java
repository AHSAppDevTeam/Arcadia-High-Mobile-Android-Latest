package com.hsappdev.ahs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsappdev.ahs.db.ArticleRepository;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.ui.calendar.CalendarState;
import com.hsappdev.ahs.ui.calendar.DayViewContainer;
import com.hsappdev.ahs.ui.calendar.MonthHeaderContainer;
import com.hsappdev.ahs.ui.calendar.ScheduleRendererView;
import com.hsappdev.ahs.viewModels.ScheduleViewModel;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    private static final String TAG = "ScheduleFragment";

    private CalendarView calendarView;
    private ScheduleRendererView scheduleRendererView;
    private CalendarState calendarState;


    private ScheduleViewModel viewModel;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the view model shared between the classes
        viewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);

        calendarState = new CalendarState(viewModel);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        scheduleRendererView = view.findViewById(R.id.scheduleRendererView);

        setUpCalendarLibrary();

        return view;
    }

    public void setUpCalendarLibrary() {
        calendarState.setCalendarView(calendarView);
        calendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<MonthHeaderContainer>() {
            @NotNull
            @Override
            public MonthHeaderContainer create(@NotNull View view) {
                return new MonthHeaderContainer(view);
            }

            @Override
            public void bind(@NotNull MonthHeaderContainer viewContainer, @NotNull CalendarMonth calendarMonth) {
                viewContainer.updateView(calendarMonth);
            }
        });
        calendarView.setDayBinder(new DayBinder<DayViewContainer>() {
            @NotNull
            @Override
            public DayViewContainer create(@NotNull View view) {
                return new DayViewContainer(view, calendarState, scheduleRendererView);
            }

            @Override
            public void bind(@NotNull DayViewContainer viewContainer, @NotNull CalendarDay calendarDay) {
                viewContainer.updateView(calendarDay);
            }
        });
        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusMonths(12);
        YearMonth lastMonth = currentMonth.plusMonths(12);
        calendarView.setup(firstMonth, lastMonth, DayOfWeek.SUNDAY);
        calendarView.scrollToMonth(currentMonth);

        calendarState.loadCalendarDataAsync(viewModel, requireActivity()); // must load data after calendar setup
    }


}