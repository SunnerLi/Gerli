package edu.sunner.parserdevelop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.sunner.parserdevelop.parser.Parser;
import edu.sunner.parserdevelop.parser.Record;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
            The usage of the parser
         */
        Parser parser = new Parser();
        try {
            Record record = parser.parse("慢思 飲料 -100");
            record.dump();

            record = parser.parse("慢思 飲料 =100");
            record.dump();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
