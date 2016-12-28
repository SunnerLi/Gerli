package com.gerli.gerli.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerli.gerli.R;

import noman.weekcalendar.WeekCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DAY extends Fragment {
    public DAY() {
        // Required empty public constructor
    }
    private WeekCalendar weekCalendar;
    private View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_day, container, false);

        //init();//當按下一個日期
        return  myView;
    }

}
