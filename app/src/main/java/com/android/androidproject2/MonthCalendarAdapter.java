package com.android.androidproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=100;
    Calendar calendar = Calendar.getInstance();

    public MonthCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        int year =calendar.get(Calendar.YEAR);//year month 값 수정
        int month = calendar.get(Calendar.MONTH) + position;
        return MonthCalenderFragment.newInstance(year, month);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}

