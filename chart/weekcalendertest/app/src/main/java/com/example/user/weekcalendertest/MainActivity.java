package com.example.user.weekcalendertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

public class MainActivity extends AppCompatActivity {
    private WeekCalendar weekCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//當按下一個日期，
    }
    private void init() {
        weekCalendar = (WeekCalendar) findViewById(R.id.weekCalendar);
        //當按到當天就會產生一個圖表
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                int year=0,month=0,date=0; //年月日，時間輸入
//                Toast.makeText(MainActivity.this, "You Selected " + dateTime.get(DateTimeFieldType.year()), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "You Selected " + dateTime.get(DateTimeFieldType.monthOfYear()), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "You Selected " + dateTime.get(DateTimeFieldType.dayOfMonth()), Toast.LENGTH_SHORT).show();
                year=dateTime.get(DateTimeFieldType.year());
                month=dateTime.get(DateTimeFieldType.monthOfYear());
                date=dateTime.get(DateTimeFieldType.dayOfMonth());
                Toast.makeText(MainActivity.this, "You Selected " + year +"/"+ month+"/"+ date , Toast.LENGTH_SHORT).show();
            }

        });

    }

}
