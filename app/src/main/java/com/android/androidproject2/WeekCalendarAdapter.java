package com.android.androidproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=100;
    public static final String[] day = {"일","월","화","수","목","금","토"};
    Calendar cal = Calendar.getInstance();

    public WeekCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int day = cal.get(Calendar.DATE);
        int weekofmonth = cal.get(Calendar.WEEK_OF_MONTH) + position;
        return WeekCalenderFragment.newInstance(weekofmonth, day);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }

}

