package com.gerli.gerli.fragment;


import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gerli.gerli.DatePickerFragment;
import com.gerli.gerli.R;
import com.gerli.handsomeboy.gerliUnit.AccountType;
import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChargeFragment extends Fragment implements DatePickerFragment.PassOnDateSetListener {

    private GerliDatabaseManager gerliDatabaseManager;
    private View myView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_charge, container, false);

        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        choseDateText.setText(CalendarManager.getDay());
        gerliDatabaseManager = new GerliDatabaseManager(getActivity().getApplicationContext());
        gerliDatabaseManager.insertAccount("嗨", 123, AccountType.BOOK, CalendarManager.getDay(), "jkl");
        Cursor mCursor = gerliDatabaseManager.getCursor_dayItem(CalendarManager.getDay());

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                R.layout.charge_list, mCursor, new String[] {"Name", "Money"}, new int[]{R.id.item_name, R.id.item_cost}, 0);
        ListView listView = (ListView) myView.findViewById(R.id.charge_list_view);
        listView.setAdapter(adapter);

        return myView;
    }

    public void passOnDateSet(int year, int month, int day) {
        TextView choseDateText = (TextView) getActivity().findViewById(R.id.choose_date_text);
        choseDateText.setText(CalendarManager.getDay(year, month, day));
        Cursor mCursor = gerliDatabaseManager.getCursor_dayItem(CalendarManager.getDay(year, month, day));

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                R.layout.charge_list, mCursor, new String[] {"Name", "Money"}, new int[]{R.id.item_name, R.id.item_cost}, 0);
        ListView listView = (ListView) myView.findViewById(R.id.charge_list_view);
        listView.setAdapter(adapter);
    }
}