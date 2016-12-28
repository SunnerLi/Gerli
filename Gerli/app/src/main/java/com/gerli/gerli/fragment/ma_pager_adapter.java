package com.gerli.gerli.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class ma_pager_adapter extends FragmentPagerAdapter {
    public ma_pager_adapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                DAY t1 = new DAY();
                return t1;
            case 1:
                WEEK t2 = new WEEK();
                return t2;
            case 2:
                 MONTH t3 = new MONTH();
                return t3;

            case 3:
                YEAR t4 = new YEAR();
                return t4;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }//set the number of tabs

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "DAY";
            case 1:

                return "WEEK";
            case 2:

                return "MONTH";
            case 3:
                return "YEAR";
        }
        return null;
    }



}
