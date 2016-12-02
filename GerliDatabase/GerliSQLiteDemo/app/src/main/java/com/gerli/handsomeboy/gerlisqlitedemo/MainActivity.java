package com.gerli.handsomeboy.gerlisqlitedemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Calendar;

import com.gerli.handsomeboy.gerliUnit.UnitPackage.*;

public class MainActivity extends AppCompatActivity {
    final String DatabaseName = "Gerli_DB";


    GerliDatabaseManager databaseManager;
    SQLiteDB myDB;
    SQLiteDatabase db;
    SimpleCursorAdapter adapter;

    ListView listView;
    Button deleteButton;
    Button insertButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        databaseManager = new GerliDatabaseManager(this);
        myDB = new SQLiteDB(this,DatabaseName,null,7);
        db = myDB.getReadableDatabase();

        ViewSetting();
        select();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        select();
    }

    void ViewSetting(){
        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delete(id);
                return false;
            }
        });

        deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();            }
        });
        insertButton = (Button)findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
    }

    void select(){
        //Cursor cursor = db.query("Account",null,null,null,null,null,null);
        //Cursor cursor = db.rawQuery( "select rowid as _id,Name,Money,Type,Time,Description from account", null);
        Cursor cursor = databaseManager.getCursor_todayItem();
        //Cursor cursor = databaseManager.getCursor_total(Info_type.EXPENSE);

        if(listView.getAdapter() == null){
            adapter = new SimpleCursorAdapter(this
                    ,R.layout.query_list_account
                    ,cursor
                    ,new String[]{"Name","Money","Type","Time","Description"}
                    ,new int[]{R.id.textView,R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5},0);
            listView.setAdapter(adapter);
        }
        else{
            adapter.changeCursor(cursor);
        }

        databaseManager.getCursor_total(Info_type.EXPENSE);
        TotalPackage totalPackage = databaseManager.getTodayTotal();
        databaseManager.getCursor_DayTotal(Info_type.EXPENSE,new CalendarManager().getDay(2016,11,23));
        databaseManager.getBarChartByWeek();
        databaseManager.getBarChart(Info_type.WEEK);
        databaseManager.getBarChartByYear();
        databaseManager.getBarChart(Info_type.YEAR);
        CalendarManager calendarManager = new CalendarManager();
        String string = calendarManager.getDay(2016,1,1);

    }

    void delete(long id){
        String id_str = Long.toString(id);
        db.delete("account","_id=" + id_str,null);
        select();
    }
    void delete(){
        db.delete("account","1",null);
        select();
    }


}
