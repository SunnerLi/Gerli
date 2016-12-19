package com.gerli.gerli.btnInput;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerli.gerli.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BtnInput_describeFragment extends DialogFragment {


    public BtnInput_describeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_btn_input_describe, container, false);
        getDialog().setTitle("請輸入帳目敘述");


        return view;
    }

}
