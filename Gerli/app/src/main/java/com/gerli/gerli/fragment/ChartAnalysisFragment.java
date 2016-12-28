package com.gerli.gerli.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gerli.gerli.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class ChartAnalysisFragment extends Fragment {
    private View myView;
    ViewPager pager;
    PagerTabStrip tab_strp;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chart_analysis, container, false);
        ma_pager_adapter mapager=new ma_pager_adapter(getFragmentManager());
        pager=(ViewPager)myView.findViewById(R.id.pager);

        pager.setAdapter(mapager);
        tab_strp=(PagerTabStrip)myView.findViewById(R.id.tab_strip);
        tab_strp.setTextColor(Color.WHITE);

        return myView;
    }


}
