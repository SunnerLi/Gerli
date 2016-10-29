package edu.sunner.parserdevelop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.sunner.parserdevelop.parser.Parser;
import edu.sunner.parserdevelop.parser.Record;
import edu.sunner.parserdevelop.parser.RemoteParser;

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

        Button send = (Button) findViewById(R.id.send);
        Button exit = (Button) findViewById(R.id.exit);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        new RemoteParser().work("The two pens is 500 dollars",
                            RemoteParser.sentence);
                    }
                }.start();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        new RemoteParser().work("exit",
                            RemoteParser.control);
                    }
                }.start();
            }
        });

    }
}
