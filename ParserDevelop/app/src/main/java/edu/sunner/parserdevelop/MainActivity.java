package edu.sunner.parserdevelop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.sunner.parserdevelop.parser.Parser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Parser parser = new Parser();
        Log.d("-----", String.valueOf(parser.parse("drink 食 ㄦ+100")));
    }
}
