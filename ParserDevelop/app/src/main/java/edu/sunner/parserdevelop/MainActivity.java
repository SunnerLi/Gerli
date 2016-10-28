package edu.sunner.parserdevelop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.sunner.parserdevelop.parser.Parser;
import edu.sunner.parserdevelop.parser.Record;

public class MainActivity extends AppCompatActivity {
    MoneyHandler moneyHandler = new MoneyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        moneyHandler.work("慢司 2 -100");
    }
}
