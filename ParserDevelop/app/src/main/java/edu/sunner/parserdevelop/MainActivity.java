package edu.sunner.parserdevelop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.sunner.parserdevelop.parser.Parser;
import edu.sunner.parserdevelop.parser.RemoteParser;

/**
 * Main activity
 *
 * Example sentence:
 * I paid 100 dollars for the card saving
 *
 * @author SunnerLi
 * @revise 10/29/2016
 */

public class MainActivity extends AppCompatActivity {
    public static EditText addr;

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
        Button send3 = (Button) findViewById(R.id.send3);
        final EditText edit3 = (EditText) findViewById(R.id.edit3);
        addr = (EditText) findViewById(R.id.addr);
        Button exit = (Button) findViewById(R.id.exit);

        // Set the listener
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //new RemoteParser().work(edit1.getText().toString(), RemoteParser.sentence);
                        new Parser().parse(edit1.getText().toString(), Parser.sentence);

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
                        //new RemoteParser().work(edit2.getText().toString(), RemoteParser.sentence);
                        new Parser().parse(edit2.getText().toString(), Parser.sentence);

                    }
                }.start();
            }
        });
        send3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //new RemoteParser().work(edit3.getText().toString(), RemoteParser.sentence);
                        new Parser().parse(edit3.getText().toString(), Parser.sentence);

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
                        //new RemoteParser().work("exit", RemoteParser.control);
                        new Parser().parse("exit", Parser.control);
                    }
                }.start();
            }
        });
    }
}
