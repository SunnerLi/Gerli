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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.gerli.gerli.DatePickerFragment;
import com.gerli.gerli.NavigationActivity;
import com.gerli.gerli.R;
import com.gerli.gerli.VoiceInputActivity;
import com.gerli.gerli.calculator.NumBtnActivity;
import com.gerli.gerli.chat.ChatInputActivity;
import com.gerli.gerli.typeImageRes;
import com.gerli.handsomeboy.gerliUnit.AccountType;
import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerliUnit.Info_type;
import com.gerli.handsomeboy.gerliUnit.Table;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChargeFragment extends Fragment implements DatePickerFragment.PassOnDateSetListener {

    private GerliDatabaseManager gerliDatabaseManager;
    private View myView;

    ImageButton buttonInput, chatInput, voiceInput;

    //資料庫要用到
    int myYear,myMonth,myDay;
    long idArr[]; //存ID  重要重要啦

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

        //設定初始日期
        Calendar calendar = Calendar.getInstance();
        myYear = calendar.get(Calendar.YEAR);
        myMonth = calendar.get(Calendar.MONTH) + 1;
        myDay = calendar.get(Calendar.DAY_OF_MONTH);

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
        //updateListView();

        return myView;
    }

    public void passOnDateSet(int year, int month, int day) {
        myYear = year;
        myMonth = month + 1;
        myDay = day;
        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setText(CalendarManager.getDay(year, month + 1, day));
        updateListView();
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
                Bundle bundle = new Bundle();
                bundle.putInt(Info_type.getInfoTypeName(Info_type.YEAR),myYear);
                bundle.putInt(Info_type.getInfoTypeName(Info_type.MONTH),myMonth);
                bundle.putInt(Info_type.getInfoTypeName(Info_type.DAY),myDay);
                intent.putExtras(bundle);
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

    public void updateListView(){
        Cursor myCursor = gerliDatabaseManager.getCursor_dayItem(CalendarManager.getDay(myYear,myMonth,myDay));

        List<Map<String, Object>> items = new ArrayList<>();
        idArr = new long[myCursor.getCount()];
        myCursor.moveToFirst();
        for (int i = 0; i < myCursor.getCount(); i++) {
            Map<String, Object> item = new HashMap<>();
            int money = myCursor.getInt(2);
            String INOUT;
            INOUT = (money > 0)?"支出":"收入";
            money = (money > 0)?money:-money;

            idArr[i] = myCursor.getLong(0);
            item.put("Name", myCursor.getString(1));
            item.put("Money", "" + money);
            item.put("Type", typeImageRes.image[myCursor.getInt(3)]);
            item.put("INOUT",INOUT);
            items.add(item);
            myCursor.moveToNext();
        }
        myCursor.close();

        SimpleAdapter adapter = new SimpleAdapter(getContext(),items, R.layout.charge_list,
                new String[] {"Name", "Money", "Type", "INOUT"},
                new int[]{R.id.item_name, R.id.item_cost, R.id.item_img, R.id.item_class});
        ListView listView = (ListView) myView.findViewById(R.id.charge_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gerliDatabaseManager.delete(Table.ACCOUNT,idArr[position]);
                updateListView();
                return false;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        updateListView();
    }
}