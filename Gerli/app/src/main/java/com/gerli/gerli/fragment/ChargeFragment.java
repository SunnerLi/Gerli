package com.gerli.gerli.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gerli.gerli.DatePickerFragment;
import com.gerli.gerli.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChargeFragment extends Fragment implements DatePickerFragment.PassOnDateSetListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_charge, container, false);

        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        return myView;
    }

    public void passOnDateSet(int year, int month, int day) {
        TextView choseDateText = (TextView) getActivity().findViewById(R.id.choose_date_text);
        choseDateText.setText("HHHHHHHHHHH");
    }
}