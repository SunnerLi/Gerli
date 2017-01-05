package com.gerli.gerli.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerli.gerli.R;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartAnalysisFragment extends Fragment {
    private View myView;
    ViewPager pager;
    PagerTabStrip tab_strp;

    private ProgressDialogFragment dlg;
    private int p = 0;
    private Handler pHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chart_analysis, container, false);

        ma_pager_adapter mapager=new ma_pager_adapter(getFragmentManager(),new GerliDatabaseManager(getContext()));

        pager=(ViewPager)myView.findViewById(R.id.pager);

        pager.setAdapter(mapager);
        tab_strp=(PagerTabStrip)myView.findViewById(R.id.tab_strip);
        tab_strp.setTextColor(Color.WHITE);

        return myView;
    }
    public  void setprosess()
    {
        pHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if ( p >= 100 ) {
                    dlg.dismiss();
//                    TextView output = (TextView) findViewById(R.id.lblOutput);
//                    output.setText("下載已完成....");
                }
                else {
                    p++;
                    dlg.updateProgress();  //  更新進度
                    pHandler.sendEmptyMessageDelayed(0,50);
                }
            }
        };
    }
}