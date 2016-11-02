package com.gerli.handsomeboy.gerlisqlitedemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    final String DatabaseName = "Gerli_DB";

    SQLiteDB myDB;
    SQLiteDatabase db;
    SimpleCursorAdapter adapter;

    ListView listView;
    Button selectButton;
    Button insertButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myDB = new SQLiteDB(this,DatabaseName,null,2);
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

        selectButton = (Button)findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select();
            }
        });
        insertButton = (Button)findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void select(){
        //Cursor cursor = db.query("Account",null,null,null,null,null,null);
        Cursor cursor = db.rawQuery( "select rowid as _id,Name,Money,Type,Time,Description from account", null);


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

    }

    void delete(long id){
        String id_str = Long.toString(id);
        db.delete("account","id=" + id_str,null);
        select();
    }


}
