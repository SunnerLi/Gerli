package com.gerli.handsomeboy.gerlisqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String DatabaseName = "Gerli_DB";

    SQLiteDB myDB;
    SQLiteDatabase db;

    //TextView
    TextView nameTextView;
    TextView moneyTextView;
    TextView typeTextView;
    TextView desTextView;
    //EditText
    EditText nameEditText;
    EditText moneyEditText;
    EditText typeEditText;
    EditText desEditText;
    //Button
    Button enterButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new SQLiteDB(this,DatabaseName,null,2);
        db = myDB.getWritableDatabase();

        ViewSetting();
        //DBInsertTest();
    }

    void ViewSetting(){
        //TextView
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        moneyTextView = (TextView)findViewById(R.id.moneyTextView);
        typeTextView = (TextView)findViewById(R.id.typeTextView);
        desTextView = (TextView)findViewById(R.id.desTextView);
        //EditText
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        moneyEditText = (EditText)findViewById(R.id.moneyEditText);
        typeEditText = (EditText)findViewById(R.id.typeEditText);
        desEditText = (EditText)findViewById(R.id.desEditText);
        //Button
        enterButton = (Button)findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues value = new ContentValues();
                SetEmptyValue();
                //value.put("Id",1);
                value.put("Name",nameEditText.getText().toString());
                value.put("Money", Integer.valueOf(moneyEditText.getText().toString()));
                value.put("Type", 1);
                value.put("Time", "2016");
                value.put("Description",desEditText.getText().toString());
                db.insert("Account",null,value);
            }
            void SetEmptyValue(){
                if(nameEditText.getText().toString().equals("")){nameEditText.setText("無");}
                if(moneyEditText.getText().toString().equals("")){moneyEditText.setText("0");}
                if(typeEditText.getText().toString().equals("")){typeEditText.setText("無");}
                if(desEditText.getText().toString().equals("")){desEditText.setText("無");}

            }
        });
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    void DBInsertTest(){
        ContentValues value = new ContentValues();
        //value.put("Id",1);
        value.put("Name","早餐");
        value.put("Money", 50);
        value.put("Type", 1);
        value.put("Time", "2016");
        value.put("Description","Test");
        db.insert("Account",null,value);
    }
}
