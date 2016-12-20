package com.gerli.gerli.btnInput;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gerli.gerli.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BtnInput_describeFragment extends DialogFragment {

    static final String MESSAGE = "MESSAGE";

    String msg;
    EditText name;
    Button button;
    public BtnInput_describeFragment() {
        // Required empty public constructor
    }

    public static BtnInput_describeFragment newInstance(String str) {
        Bundle args = new Bundle();
        args.putString(MESSAGE,str);
        BtnInput_describeFragment fragment = new BtnInput_describeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            msg = getArguments().getString(MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_btn_input_describe, container, false);
        getDialog().setTitle("請輸入帳目敘述");

        name = (EditText)view.findViewById(R.id.des_editText);
        name.requestFocus();
        if(msg != null){
            name.setText(msg);
            name.setSelection(name.getText().length());
        }
        button = (Button)view.findViewById(R.id.des_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(view);
            }
        });

        return view;
    }

    public void buttonClicked(View view) {
        DesDialFragListener activity = (DesDialFragListener) getActivity();
        activity.onFinishEditDescription(name.getText().toString());
        this.dismiss();
    }

}
