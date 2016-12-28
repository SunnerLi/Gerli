package com.gerli.gerli.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerli.gerli.R;
import com.gerli.gerli.btnInput.BtnInputActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthPlanFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = new Intent(getActivity(), BtnInputActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("DOLLAR",0.0);
        bundle.putBoolean("INOUT",true);
        intent.putExtras(bundle);
        startActivity(intent);

        return inflater.inflate(R.layout.fragment_month_plan, container, false);
    }

}
