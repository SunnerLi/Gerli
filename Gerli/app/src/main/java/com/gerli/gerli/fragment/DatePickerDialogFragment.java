package com.gerli.gerli.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {
    Calendar dt = Calendar.getInstance();
    DAY getFragment;
    final static String bundleKey = "DAY_FRAG";
    static DatePickerDialogFragment newInstance(DAY date) {
        DatePickerDialogFragment dlg = new DatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(bundleKey,date);
        dlg.setArguments(bundle);
        return dlg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!= null){
            getFragment = (DAY) getArguments().getSerializable(bundleKey);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dDialog = new DatePickerDialog(getActivity(),
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    getFragment.getDate(year, monthOfYear, dayOfMonth);
                }
            }, dt.get(Calendar.YEAR),
            dt.get(Calendar.MONTH),
            dt.get(Calendar.DAY_OF_MONTH));
        return dDialog;
    }
}
