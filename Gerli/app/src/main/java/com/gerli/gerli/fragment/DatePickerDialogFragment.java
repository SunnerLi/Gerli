package com.gerli.gerli.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {
    Calendar dt = Calendar.getInstance();
    static DatePickerDialogFragment newInstance() {
        DatePickerDialogFragment dlg = new DatePickerDialogFragment();
        return dlg;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dDialog = new DatePickerDialog(getActivity(),
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    DAY.getDate(year, monthOfYear, dayOfMonth);
                }
            }, dt.get(Calendar.YEAR),
            dt.get(Calendar.MONTH),
            dt.get(Calendar.DAY_OF_MONTH));
        return dDialog;
    }
}
