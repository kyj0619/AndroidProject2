package com.android.androidproject2;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekCalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekCalenderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    Calendar cal = Calendar.getInstance();
    public WeekCalenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeekCalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekCalenderFragment newInstance(int param1, int param2) {
        WeekCalenderFragment fragment = new WeekCalenderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<String> Agrid = new ArrayList<>();
        //날짜 초기화
        cal.set(Calendar.DATE,mParam2);
        cal.set(Calendar.WEEK_OF_MONTH,mParam1);
        cal.set(Calendar.DAY_OF_WEEK,1);
        //주간 날짜 표시
        int count = 0,max=0;
        int day = cal.get(Calendar.DATE);
        int maxday = cal.getActualMaximum(Calendar.DATE);
        while(count !=7){
            if(day>maxday){
                cal.set(Calendar.DATE,1);
                day = cal.get(Calendar.DATE);
                list.add(String.valueOf(day++));
            }
            else {
                list.add(String.valueOf(day++));
            }
            count++;
        }
        while (max!=168){
            Agrid.add("");
            max++;
        }

        View rootView = inflater.inflate(R.layout.fragment_week_calender, container, false);
        GridView numgrid = rootView.findViewById(R.id.numofweek);
        GridView Tgrid = rootView.findViewById(R.id.grid);
        numgrid.setAdapter(new ArrayAdapter<String>(
                getActivity(),  // 현재 프래그먼트 연결된 액티비티
                android.R.layout.simple_list_item_activated_1,
                list));

        Tgrid.setAdapter(new ArrayAdapter<String>(
                getActivity(),  // 현재 프래그먼트 연결된 액티비티
                android.R.layout.simple_list_item_activated_1,
                Agrid));

        Tgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "position="+ position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}