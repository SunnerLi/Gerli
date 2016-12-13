package com.gerli.gerli.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        Button choseDateButton = (Button) myView.findViewById(R.id.choose_date_button);
        choseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        return myView;
    }

    public void passOnDateSet(int year, int month, int day) {
        Button choseDateButton = (Button) getActivity().findViewById(R.id.choose_date_button);
        choseDateButton.setText("HHHHHHHHHHH");
    }
}