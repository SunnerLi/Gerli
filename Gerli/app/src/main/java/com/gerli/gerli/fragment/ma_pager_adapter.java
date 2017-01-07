package com.gerli.gerli.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import java.util.Locale;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class ma_pager_adapter extends FragmentPagerAdapter {
    public ma_pager_adapter(FragmentManager fm,GerliDatabaseManager gerli) {
        super(fm);
        manager = gerli;
    }

    public GerliDatabaseManager manager;

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                DAY t1 = new DAY();
                Log.d("DATATATATA","1");
//                Bundle bundle1 = new Bundle();
//                bundle1.putSerializable("database",manager);
//                t1.setArguments(bundle1);
                return t1;
            case 1:
                WEEK t2 = new WEEK();
                Log.d("DATATATATA","2");
//                Bundle bundle2 = new Bundle();
//                bundle2.putSerializable("database",manager);
//                t2.setArguments(bundle2);
                return t2;
            case 2:
                 MONTH t3 = new MONTH();
                Log.d("DATATATATA","3");
//                Bundle bundle3 = new Bundle();
//                bundle3.putSerializable("database",manager);
//                t3.setArguments(bundle3);
                return t3;

            case 3:
                YEAR t4 = new YEAR();
                Log.d("DATATATATA","4");
//                Bundle bundle4 = new Bundle();
//                bundle4.putSerializable("database",manager);
//                t4.setArguments(bundle4);
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        //manager.close();
    }

}
