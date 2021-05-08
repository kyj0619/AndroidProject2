package com.android.androidproject2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.DATE;
import static java.util.Calendar.YEAR;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthCalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthCalenderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    Calendar calendar = Calendar.getInstance();
    ArrayList<String> day = new ArrayList<>();
    public MonthCalenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MonthCalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthCalenderFragment newInstance(int year, int month) {
        MonthCalenderFragment fragment = new MonthCalenderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        calendar.set(YEAR,mParam1);
        calendar.set(Calendar.MONTH,mParam2);
        calendar.set(Calendar.DAY_OF_MONTH,1);

        int startDay = calendar.get(Calendar.DAY_OF_WEEK); //이달 시작하는 요일 구하기
        int lastDay = calendar.getActualMaximum(DATE);  //달의 마지막 날짜를 계산하는 메소드

        calendar.set(Calendar.DATE,1);
        int firstday = calendar.get(Calendar.DATE);

            for (int i = 1; i < startDay; i++) {
                day.add("");
            }
            for (int i = startDay - 1; i < startDay -1 + lastDay; i++) {
                day.add(String.valueOf(firstday++));
            }


        View rootView = inflater.inflate(R.layout.fragment_month_calender, container, false);
        GridView daygrid = rootView.findViewById(R.id.day);
        daygrid.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, day));

        daygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String today = day.get(position);
                int year =calendar.get(YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                String now = Integer.toString(year)+"."+Integer.toString(month)+"."+(today);
                Toast.makeText(getContext(), now, Toast.LENGTH_SHORT).show();
            }
        });

        MainActivity activity = (MainActivity)getActivity();
        int year =calendar.get(YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        String now = Integer.toString(year)+"년"+Integer.toString(month)+"월";
        activity.setActionBarTitle(now);

        daygrid.setBackgroundResource(R.drawable.gridline);

        return rootView;
    }
}