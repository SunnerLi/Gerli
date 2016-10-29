package edu.sunner.parserdevelop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import edu.sunner.parserdevelop.parser.RemoteParser;

/**
 * Main activity
 *
 * @author SunnerLi
 * @revise 10/29/2016
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Default onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Set the click listener
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Get the widget
        Button send1 = (Button) findViewById(R.id.send1);
        final EditText edit1 = (EditText) findViewById(R.id.edit1);
        Button send2 = (Button) findViewById(R.id.send2);
        final EditText edit2 = (EditText) findViewById(R.id.edit2);
        Button exit = (Button) findViewById(R.id.exit);

        // Set the listener
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        new RemoteParser().work(edit1.getText().toString(), RemoteParser.sentence);

                    }
                }.start();
            }
        });
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        new RemoteParser().work(edit2.getText().toString(), RemoteParser.sentence);

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
                        new RemoteParser().work("exit", RemoteParser.control);
                    }
                }.start();
            }
        });
    }
}
