package com.gerli.gerli.fragment;


import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gerli.gerli.DatePickerFragment;
import com.gerli.gerli.NavigationActivity;
import com.gerli.gerli.R;
import com.gerli.gerli.VoiceInputActivity;
import com.gerli.gerli.calculator.NumBtnActivity;
import com.gerli.gerli.chat.ChatInputActivity;
import com.gerli.handsomeboy.gerliUnit.AccountType;
import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChargeFragment extends Fragment implements DatePickerFragment.PassOnDateSetListener {

    private GerliDatabaseManager gerliDatabaseManager;
    private View myView;

    ImageButton buttonInput, chatInput, voiceInput;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_charge, container, false);
        setButton();

        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        choseDateText.setText(CalendarManager.getDay());
        gerliDatabaseManager = new GerliDatabaseManager(getContext());
        //gerliDatabaseManager.insertAccount("嗨", 123, AccountType.BOOK, CalendarManager.getDay(), "jkl");
        Cursor mCursor = gerliDatabaseManager.getCursor_dayItem(CalendarManager.getDay());

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.charge_list, mCursor,
                new String[] {"Name", "Money"}, new int[]{R.id.item_name, R.id.item_cost}, 0);
        ListView listView = (ListView) myView.findViewById(R.id.charge_list_view);
        listView.setAdapter(adapter);

        return myView;
    }

    public void passOnDateSet(int year, int month, int day) {
        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setText(CalendarManager.getDay(year, month + 1, day));
        Cursor mCursor = gerliDatabaseManager.getCursor_dayItem(CalendarManager.getDay(year, month + 1, day));

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(),
                R.layout.charge_list, mCursor, new String[] {"Name", "Money"}, new int[]{R.id.item_name, R.id.item_cost}, 0);
        ListView listView = (ListView) myView.findViewById(R.id.charge_list_view);
        listView.setAdapter(adapter);
    }


    /**
     * Set the function of the button
     */

    public void setButton(){
        buttonInput = (ImageButton) myView.findViewById(R.id.buttonInput);
        voiceInput = (ImageButton) myView.findViewById(R.id.voiceInput);
        chatInput = (ImageButton) myView.findViewById(R.id.chatInput);

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), NumBtnActivity.class);
                startActivity(intent);
            }
        });
        voiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), VoiceInputActivity.class);
                startActivity(intent);
            }
        });
        chatInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ChatInputActivity.class);
                startActivity(intent);
            }
        });
    }
}