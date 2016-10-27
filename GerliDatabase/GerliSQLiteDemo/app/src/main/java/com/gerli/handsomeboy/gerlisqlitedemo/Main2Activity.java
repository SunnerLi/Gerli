package com.gerli.handsomeboy.gerlisqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    final String DatabaseName = "Gerli_DB";

    SQLiteDB myDB;
    SQLiteDatabase db;


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myDB = new SQLiteDB(this,DatabaseName,null,2);
        db = myDB.getWritableDatabase();

        ViewSetting();
    }

    void ViewSetting(){
        listView = (ListView)findViewById(R.id.listView);

        Cursor cursor = db.query("Account",null,null,null,null,null,null);
    }


}
