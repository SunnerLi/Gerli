package com.gerli.gerli.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gerli.gerli.R;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

public class MPinputActivity extends AppCompatActivity {
    private String output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpinput);
        int year,month,date;
        //取得傳遞資料
        Intent myIntent = this.getIntent();
        year = myIntent.getIntExtra("YEAR",1);
        month = myIntent.getIntExtra("MONTH",1);
        date = myIntent.getIntExtra("DATE",1);

        //資料庫輸入
        GerliDatabaseManager manager = new GerliDatabaseManager(this);
        //manager.insertMonthPlan(2016,12,21,"柳隨雲好帥");//新增一筆月計畫

        EditText edtx1=(EditText)findViewById(R.id.editText1);
        output = edtx1.getText().toString();
        manager.insertMonthPlan(year,month,date,output);
    }

    public void button2_Click(View view) {
        Intent replyIntent = new Intent();
        // 建立傳回值
        Bundle bundle = new Bundle();
        bundle.putString("MPINPUT", output);
        replyIntent.putExtras(bundle);  // 加上資料
        setResult(RESULT_OK, replyIntent); // 設定傳回
        finish(); // 結束活動
    }
}
